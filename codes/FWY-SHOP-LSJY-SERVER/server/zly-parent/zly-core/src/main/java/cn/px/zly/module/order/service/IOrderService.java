package cn.px.zly.module.order.service;

import cn.px.zly.module.order.entity.OrderPO;
import cn.px.zly.module.order.param.TradeOrderParam;
import cn.px.zly.module.order.param.TradePayParam;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IOrderService {

    long createOrder(TradeOrderParam orderParam);

    OrderPO getOrder(long orderId);

    void handlePayNotify(TradePayParam payParam);

    List<OrderPO> queryTopayOrder();

    void remove(long orderId);

    OrderPO getAndCheckOrderPay(long userId, long orderId);
}
