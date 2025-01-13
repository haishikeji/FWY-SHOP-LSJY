package cn.px.zly.module.box.service.impl;

import cn.px.core.spring.EnvironmentContext;
import cn.px.core.util.DateFormatUtil;
import cn.px.core.util.DateUtil;
import cn.px.zly.module.box.entity.*;
import cn.px.zly.module.box.ienum.*;
import cn.px.zly.module.box.service.*;
import cn.px.zly.module.box.entity.*;
import cn.px.zly.module.box.ienum.*;
import cn.px.zly.module.box.service.*;
import cn.px.zly.module.order.entity.OrderPO;
import cn.px.zly.module.order.ienum.OrderStatusEnum;
import cn.px.zly.module.order.service.IOrderService;
import cn.px.zly.util.WeightedRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class BeerBoxOrderContext implements IBeerBoxOrderContext {

    @Autowired
    private IBeerBoxOrderSplitService beerBoxOrderSplitService;

    @Autowired
    private IBeerBlindBoxService beerBlindBoxService;

    @Autowired
    private IBeerBoxDetailService boxDetailService;

    @Autowired
    private IBoxBeerNumManagerService boxBeerNumManagerService;

    @Autowired
    private IBeerBoxNodeLogService beerBoxNodeLogService;

    @Autowired
    private IBeerBoxOrderService beerBoxOrderService;

    @Autowired
    private IOrderService orderService;


    @Override
    public void cutOffDayBoxInfoHandle(Date now) {
        List<BeerBlindBox> list = beerBlindBoxService.list(BlindBoxStatusEnum.Normal);
        //筛选出到截止日的盲盒
        for (BeerBlindBox box : list) {
            Integer cutOffTime = box.getCutOffTime();
            String currBoxNo = box.getCurrBoxNo();
            Date cutOffMonth = DateFormatUtil.parse(box.getCurrBoxNo(), DateFormatUtil.Pattern.YYYYMM);
            Date cutOffDate = DateUtil.plusDays(DateUtil.getFirstDateOfMonth(cutOffMonth),
                    cutOffTime - 1);
            String nextBoxNo = DateFormatUtil.format(DateUtil.plusMonths(now, 1), DateFormatUtil.Pattern.YYYYMM);
            //截止日7天前生成下一期盲盒
            int pretreatmentDay = EnvironmentContext.isProdEnv() ? 7 : 1;
            Date genNextBoxTime = DateUtil.plusDays(cutOffDate, -pretreatmentDay);
            BeerBoxDetail currBoxDetail = boxDetailService.getByBoxIdAndBoxNo(box.getBlindBoxId(), currBoxNo);
            boolean isLoop = !box.getLoopType().equals(BeerBoxLoopType.ONCE);
            if (DateUtil.daysCompare(now,genNextBoxTime) == 0
                    && isLoop) {
                /**
                 * 生成下一期盲盒
                 */
                BeerBoxDetail beerBoxDetail = null;
                BeerBoxDetail newDetail = boxDetailService.getByBoxIdAndBoxNo(box.getBlindBoxId(),nextBoxNo);
                if (newDetail == null ) {

                    beerBoxDetail = new BeerBoxDetail();
                    beerBoxDetail.setBoxDetailId(null);
                    beerBoxDetail.setGmtCreate(null);
                    beerBoxDetail.setGmtModified(null);
                    beerBoxDetail.setBoxNo(nextBoxNo);
                    beerBoxDetail.setBoxId(box.getBlindBoxId());
                    beerBoxDetail.setTitle(currBoxDetail.getTitle());
                    beerBoxDetail.setDetails(currBoxDetail.getDetails());
                    beerBoxDetail.setDes(currBoxDetail.getDetails());
                    beerBoxDetail.setSkuIds(currBoxDetail.getSkuIds());
                    boxDetailService.save(beerBoxDetail);

                    //TODO 复制上一期精酿数量 boxBeerNumManagerService
                    //上一期盲盒订阅的精酿数量
                    List<BoxBeerNumManager> boxBeerNum = boxBeerNumManagerService.getBoxBeerNumByBoxId(box.getBoxDetailId());
                    //复制到下一期
                    List<BoxBeerNumManager> nextBeerBoxDetailBeerNum = new ArrayList<>();
                    for (BoxBeerNumManager oldBeerNum : boxBeerNum) {
                        BoxBeerNumManager beerNum = new BoxBeerNumManager();
                        beerNum.setBeerId(oldBeerNum.getBeerId());
                        beerNum.setNum(oldBeerNum.getNum());
                        beerNum.setBoxDetailId(beerBoxDetail.getBoxDetailId());
                        beerNum.setSkuId(oldBeerNum.getSkuId());
                        nextBeerBoxDetailBeerNum.add(beerNum);
                    }
                    boxBeerNumManagerService.batchSave(nextBeerBoxDetailBeerNum);

                }
            }
            //TODO 检查是否生成下一期盲盒，若未生成需要生成预警消息
            if (DateUtil.daysCompare(now, cutOffDate) > 0
                    && DateUtil.daysCompare(genNextBoxTime, now) > 0
                    && isLoop) {

            }
            //循环的盲盒到截止日期后
            if (DateUtil.daysCompare(cutOffDate,now) >= 0 && isLoop) {
                /*
                 *  1。更正盲盒期数为下一期，若未生成下一期盲盒
                 *  2。盲盒订单状态修改
                 */
                BeerBoxDetail nextBoxDetail = boxDetailService.getByBoxIdAndBoxNo(box.getBlindBoxId(), nextBoxNo);
                if (nextBoxDetail == null) {
                    //TODO 下一期盲盒未生成，发送短信？
                    log.error("####################################next box detail is null ! nextBoxNo{},boxId{}",
                            nextBoxNo, box.getBlindBoxId());
                    continue;
                }
                box.setCurrBoxNo(nextBoxNo);
                box.setBoxDetailId(nextBoxDetail.getBoxDetailId());
                beerBlindBoxService.update(box);
            }
            //不循环的盲盒应该修改状态为已截止
            if(DateUtil.daysCompare(now,cutOffDate) == 0 && !isLoop){
                box.setStatus(BlindBoxStatusEnum.CutOff);
                beerBlindBoxService.update(box);
            }
            /**
             * 盲盒订单预处理，生成随机的精酿
             */
            BeerBoxDetail boxDetail = currBoxDetail;
            List<BoxBeerNumManager> beerNumList = boxBeerNumManagerService.getBoxBeerNumByBoxId(boxDetail.getBoxDetailId());

            List<BeerBoxOrder> beerBoxOrders = beerBoxOrderService.listByBeerBoxId(box.getBlindBoxId(), BeerBoxOrderStatus.Payed);
            if (!beerBoxOrders.isEmpty()) {
                for (BeerBoxOrder boxOrder : beerBoxOrders) {
                    Long userAddressId = boxOrder.getUserAddressId();
                    List<BeerBoxOrderSplit> beerBoxOrderSplits = beerBoxOrderSplitService.queryByBoxOrderId(boxOrder.getBeerBoxOrderId());
                    if(!beerBoxOrderSplits.isEmpty()){
                        for (BeerBoxOrderSplit bbos : beerBoxOrderSplits) {
                            //截止日当天
                            if(DateUtil.daysCompare(now,cutOffDate) == 0){
                                if(!bbos.getStatus().equals(BeerBoxOrderSplitStatus.Finish)
                                        && bbos.getStatus().getNum() < BeerBoxOrderSplitStatus.Shipped.getNum()){
                                    //下个月份周期的订单需要更新为认购中
                                    if(bbos.getMonth().equals(nextBoxNo) && bbos.getStatus().equals(BeerBoxOrderSplitStatus.WaitingForOpen)){
                                        bbos.setStatus(BeerBoxOrderSplitStatus.OfferToBuy);
                                        bbos.setNodeId(NodeEnum.BuySuccess.getNum());
                                        beerBoxOrderSplitService.update(bbos);
                                        beerBoxNodeLogService.saveLog(NodeEnum.BuySuccess,bbos.getBbosId(),null);
                                    }
//                                    //检查是否分配精酿
//                                    if(bbos.getSkuId() != null
//                                            && BeerBoxOrderSplitStatus.WaitForDistribution.equals(bbos.getStatus())){
//
//                                        bbos.setStatus(BeerBoxOrderSplitStatus.WaitForSend);
//                                        bbos.setNodeId(NodeEnum.WaitForSend.getNum());
//                                        beerBoxOrderSplitService.update(bbos);
//                                        beerBoxNodeLogService.saveLog(NodeEnum.WaitForSend, bbos.getBbosId(), userAddressId);
//                                    }
                                    if(!beerNumList.isEmpty() && bbos.getMonth().equals(currBoxNo)){
                                        //权重map
                                        Map<Long, Double> map = new HashMap<>();
                                        int sumNum = 0;
                                        sumNum = putWeightedMap(beerNumList, map, sumNum);
                                        //查询需要处理的订单
                                        if(beerBoxOrders.isEmpty()){
                                            continue;
                                        }

                                        int bbosStatusNum = bbos.getStatus().getNum();
                                        if(bbosStatusNum >= BeerBoxOrderSplitStatus.WaitForDistribution.getNum()
                                                || bbosStatusNum == 0){
                                            continue;
                                        }
                                        if(sumNum == 0){
                                            sumNum = putWeightedMap(beerNumList, map, sumNum);
                                        }
                                        //修改状态
                                        bbos.setStatus(BeerBoxOrderSplitStatus.WaitForDistribution);
                                        //确定精酿
                                        Long skuId = WeightedRandom.getResult(map);
                                        bbos.setSkuId(skuId);
                                        Double num = map.get(skuId);
                                        map.put(skuId,--num);
                                        --sumNum;
                                        bbos.setNodeId(NodeEnum.CutOff.getNum());
                                        beerBoxOrderSplitService.update(bbos);
                                        beerBoxNodeLogService.saveLog(NodeEnum.CutOff,bbos.getBbosId(),null);
                                    }
                                }
                            }
//                            else if (DateUtil.daysCompare(now,genNextBoxTime) == 0){
//                                生成下一期盲盒当天
//
//                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void clearTopayOrder() {
        List<OrderPO> orderPOS = orderService.queryTopayOrder();
        for (OrderPO order : orderPOS) {
            if(order.getStatus().equals(OrderStatusEnum.Topay)
                    && DateUtil.daysCompare(order.getGmtCreate(),DateUtil.now()) > 5){
                orderService.remove(order.getOrderId());
                beerBoxOrderService.removeByOrderNo(order.getOrderNo());
            }
        }
    }

    @Override
    public void autoFinishedSplitOrder(Date now) {
        //已发货订单处理
        //查询发货状态订单，且发货时间超过14天的订单
        int autoFinishDay = EnvironmentContext.isProdEnv() ? 14 : 1;
        Date date = DateUtil.plusDays(now, -autoFinishDay);
        String dateStr = DateFormatUtil.format(date, DateFormatUtil.Pattern.YYYY_MM_DD);
        List<BeerBoxOrderSplit> beerBoxOrderSplits = beerBoxOrderSplitService.queryByDeliveryTimeAndStatus(dateStr, BeerBoxOrderSplitStatus.Shipped);
        if(!beerBoxOrderSplits.isEmpty()){
            for (BeerBoxOrderSplit bbos : beerBoxOrderSplits) {
                //1.确认是否超过14天
                Date deliveryTime = bbos.getDeliveryTime();
                int daysCompare = DateUtil.daysCompare(deliveryTime, now);

                if(daysCompare >= autoFinishDay
                        && BeerBoxOrderSplitStatus.Shipped.equals(bbos.getStatus())){
                    //修改状态为已完成
                    Date finishTime = DateUtil.plusDays(deliveryTime, autoFinishDay);
                    bbos.setFinishTime(finishTime);
                    bbos.setStatus(BeerBoxOrderSplitStatus.Finish);
                    bbos.setNodeId(NodeEnum.Finish.getNum());
                    beerBoxOrderSplitService.update(bbos);
                    beerBoxNodeLogService.saveLog(NodeEnum.Finish,bbos.getBbosId(),null);
                    //检查该该拆分订单是否为所属的盲盒订单的最后一个未完成订单，若是则修改该盲盒订单为已完成状态
                    List<BeerBoxOrderSplit> checkBoxOrderList = beerBoxOrderSplitService.listByBoxOrderId(bbos.getBoxOrderId());
                    boolean allFinished = false;
                    for (BeerBoxOrderSplit checkBbos : checkBoxOrderList) {
                        allFinished = allFinished && (BeerBoxOrderSplitStatus.Finish.equals(checkBbos.getStatus()) || BeerBoxOrderSplitStatus.Refund.equals(checkBbos.getStatus()));
                    }
                    if(allFinished){
                        BeerBoxOrder boxOrder = beerBoxOrderService.getByOrderNo(bbos.getOrderNo());
                        boxOrder.setStatus(BeerBoxOrderStatus.Finish);
                        beerBoxOrderService.update(boxOrder);
                    }
                }
            }
        }
    }

    private int putWeightedMap(List<BoxBeerNumManager> beerNumList, Map<Long, Double> map, int sumNum) {
        for (BoxBeerNumManager boxBeerNumManager : beerNumList) {
            Integer num = boxBeerNumManager.getNum();
            map.put(boxBeerNumManager.getSkuId(), num.doubleValue());
            sumNum += num;
        }
        return sumNum;
    }

}
