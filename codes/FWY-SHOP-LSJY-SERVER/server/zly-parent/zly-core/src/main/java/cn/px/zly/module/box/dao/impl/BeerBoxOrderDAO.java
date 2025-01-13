package cn.px.zly.module.box.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.box.dao.IBeerBoxOrderDAO;
import cn.px.zly.module.box.entity.BeerBoxOrder;
import cn.px.zly.module.box.ienum.BeerBoxOrderStatus;
import cn.px.zly.module.box.ienum.OrderStatusTypeEnum;
import cn.px.zly.module.box.param.BoxOrderListParam;
import cn.px.zly.module.order.ienum.OrderStatusEnum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class BeerBoxOrderDAO extends BaseDAOImpl<BeerBoxOrderDAO.BeerBoxOrderMapper, BeerBoxOrder> implements IBeerBoxOrderDAO {

    @Override
    public BeerBoxOrder getByOrderNo(String orderNo) {
        LambdaQueryWrapper<BeerBoxOrder> qw = Wrappers.<BeerBoxOrder>lambdaQuery();
        qw.eq(BeerBoxOrder::getOrderNo,orderNo);
        return super.getOne(qw);
    }

    @Override
    public boolean updateStatus(long beerBoxOrderId, OrderStatusEnum status) {
        LambdaUpdateWrapper<BeerBoxOrder> uw = Wrappers.<BeerBoxOrder>lambdaUpdate();
        uw.eq(BeerBoxOrder::getBeerBoxOrderId,beerBoxOrderId)
            .set(BeerBoxOrder::getStatus,status);
        return super.update(uw);
    }

    @Override
    public List<BeerBoxOrder> list(BoxOrderListParam param) {
        LambdaQueryWrapper<BeerBoxOrder> qw = Wrappers.<BeerBoxOrder>lambdaQuery();
        qw.eq(BeerBoxOrder::getUserId,param.getUserId())
            .orderByDesc(BeerBoxOrder::getGmtCreate);
        OrderStatusTypeEnum type = param.getType();
        if(param.getLastBeerBoxOrderId() != null && param.getLastBeerBoxOrderId() > 0l){
            qw.lt(BeerBoxOrder::getBeerBoxOrderId,param.getLastBeerBoxOrderId());
        }
        switch (type) {
            case RUN_TIME:qw.in(BeerBoxOrder::getStatus, BeerBoxOrderStatus.Payed,
                    BeerBoxOrderStatus.AskRefund);break;
            case FINISH:qw.in(BeerBoxOrder::getStatus,BeerBoxOrderStatus.Finish,
                    BeerBoxOrderStatus.PartialRefund,BeerBoxOrderStatus.Refund);break;
            case ALL:qw.notIn(BeerBoxOrder::getStatus, BeerBoxOrderStatus.Topay);break;
        }
        qw.last("LIMIT 20");
        return this.list(qw);
    }

    @Override
    public List<BeerBoxOrder> listByBeerBoxId(Long blindBoxId, BeerBoxOrderStatus ...statuses) {
        LambdaQueryWrapper<BeerBoxOrder> qw = Wrappers.<BeerBoxOrder>lambdaQuery();
        qw.eq(BeerBoxOrder::getBeerBoxId,blindBoxId)
            .in(BeerBoxOrder::getStatus, statuses);

        return this.list(qw);
    }

    @Mapper
    @Component
    interface BeerBoxOrderMapper extends BaseMapper<BeerBoxOrder> {
    }
}
