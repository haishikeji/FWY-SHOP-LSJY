package cn.px.zly.module.box.service.impl;

import cn.px.core.util.DateFormatUtil;
import cn.px.core.util.DateUtil;
import cn.px.core.util.Splitable;
import cn.px.zly.module.box.dao.IBeerBoxOrderSplitDAO;
import cn.px.zly.module.box.entity.BeerBlindBox;
import cn.px.zly.module.box.entity.BeerBoxOrder;
import cn.px.zly.module.box.entity.BeerBoxOrderSplit;
import cn.px.zly.module.box.ienum.BeerBoxOrderSplitStatus;
import cn.px.zly.module.box.ienum.BeerBoxOrderStatus;
import cn.px.zly.module.box.ienum.NodeEnum;
import cn.px.zly.module.box.service.IBeerBlindBoxService;
import cn.px.zly.module.box.service.IBeerBoxNodeLogService;
import cn.px.zly.module.box.service.IBeerBoxOrderSplitService;
import cn.px.zly.util.ColumnUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class BeerBoxOrderSplitService implements IBeerBoxOrderSplitService {

    @Autowired
    private IBeerBoxOrderSplitDAO beerBoxOrderSplitDAO;

    @Autowired
    private IBeerBoxNodeLogService beerBoxNodeLogService;

    @Autowired
    private IBeerBlindBoxService beerBlindBoxService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void genBeerBoxOrderSplit(BeerBoxOrder beerBoxOrder) {
        BeerBoxOrderStatus status = beerBoxOrder.getStatus();
        if(!status.equals(BeerBoxOrderStatus.Payed)){
            log.error("genBeerBoxOrderSplit error ! status:{}",status);
            return;
        }
        int monthNum = beerBoxOrder.getMonthNum();
        int num = beerBoxOrder.getNum();
        int size = monthNum * num;
        Date now = DateUtil.now();
        String boxNo = beerBoxOrder.getBoxNo();
        String month = boxNo;
        BeerBlindBox box = beerBlindBoxService.getById(beerBoxOrder.getBeerBoxId());
        String boxName = box.getBoxName();
        Integer cutOffTime = box.getCutOffTime();
        Date parseDate = DateFormatUtil.parse(month, DateFormatUtil.Pattern.YYYYMM);
        Date firstDateOfMonth = DateUtil.getFirstDateOfMonth(parseDate);
        Date cutOffDate = DateUtil.plusDays(firstDateOfMonth, cutOffTime - 1);

        for (int m = monthNum; m > 0; m--) {
            Date currCutOffDate = DateUtil.plusMonths(cutOffDate,m-1);
            Date monthDate = DateUtil.plusMonths(parseDate, m-1);
            month = DateFormatUtil.format(monthDate, DateFormatUtil.Pattern.YYYYMM);
            for (int n = num; n > 0; n--) {
                BeerBoxOrderSplit bbos = wrapBbos(beerBoxOrder);
                bbos.setBuyTime(now);
                bbos.setMonth(month);
                bbos.setBoxName(boxName);
                bbos.setCutOffTime(currCutOffDate);
                if(DateUtil.monthsCompare(now,monthDate) < 2 && m == 1){
                    bbos.setStatus(BeerBoxOrderSplitStatus.OfferToBuy);
                    bbos.setNodeId(NodeEnum.BuySuccess.getNum());
                }
                beerBoxOrderSplitDAO.save(bbos);
                //节点日志
                beerBoxNodeLogService.saveLog(NodeEnum.StayOpen,bbos.getBbosId(),null);
                if(DateUtil.monthsCompare(now,monthDate) < 2 && m == 1){
                    beerBoxNodeLogService.saveLog(NodeEnum.BuySuccess,bbos.getBbosId(),null);
                }
            }
        }
    }

    @Override
    public List<BeerBoxOrderSplit> queryByBoxOrderIdAndBoxNo(Long beerBoxOrderId, String boxNo) {
        Map<String, Object> columnMap = new HashMap();
        columnMap.put(ColumnUtil.getFieldName(BeerBoxOrderSplit::getBoxOrderId, Splitable.ATTRIBUTE_SPLIT),beerBoxOrderId);
        columnMap.put(ColumnUtil.getFieldName(BeerBoxOrderSplit::getMonth, Splitable.ATTRIBUTE_SPLIT),boxNo);
        return (List<BeerBoxOrderSplit>) beerBoxOrderSplitDAO.listByMap(columnMap);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateBatch(List<BeerBoxOrderSplit> updateBbosList) {
        updateBatch(updateBbosList);
    }

    @Override
    public List<BeerBoxOrderSplit> listByBoxOrderId(long beerBoxOrderId) {

        return beerBoxOrderSplitDAO.listByBoxOrderId(beerBoxOrderId);
    }

    @Override
    public BeerBoxOrderSplit getById(long boxOrderSplitId) {
        return beerBoxOrderSplitDAO.getById(boxOrderSplitId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean update(BeerBoxOrderSplit po) {

        return beerBoxOrderSplitDAO.updateById(po);
    }

    @Override
    public List<BeerBoxOrderSplit> queryByBoxOrderId(Long boxOrderId) {
        if(boxOrderId == null){
            return Collections.EMPTY_LIST;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put(ColumnUtil.getFieldName(BeerBoxOrderSplit::getBoxOrderId,Splitable.ATTRIBUTE_SPLIT),boxOrderId);

        return (List<BeerBoxOrderSplit>) beerBoxOrderSplitDAO.listByMap(map);
    }

    @Override
    public List<BeerBoxOrderSplit> queryByDeliveryTimeAndStatus(String dateStr, BeerBoxOrderSplitStatus status) {
        return beerBoxOrderSplitDAO.queryByDeliveryTimeAndStatus(dateStr,status);
    }

    private BeerBoxOrderSplit wrapBbos(BeerBoxOrder beerBoxOrder) {
        BeerBoxOrderSplit bbos = new BeerBoxOrderSplit();
        bbos.setBoxOrderId(beerBoxOrder.getBeerBoxOrderId());
        bbos.setOrderNo(beerBoxOrder.getOrderNo());
        bbos.setConsignee(beerBoxOrder.getName());
        bbos.setPhone(beerBoxOrder.getPhone());
        bbos.setAddress(beerBoxOrder.getAddress());
        bbos.setStatus(BeerBoxOrderSplitStatus.WaitingForOpen);
        bbos.setNodeId(NodeEnum.StayOpen.getNum());
        return bbos;

    }
}
