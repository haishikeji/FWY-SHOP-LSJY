package cn.px.zly.module.box.service;

import cn.px.zly.module.box.entity.BeerBoxOrder;
import cn.px.zly.module.box.ienum.BeerBoxOrderStatus;
import cn.px.zly.module.box.param.BoxOrderListParam;
import cn.px.zly.module.order.ienum.OrderStatusEnum;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBeerBoxOrderService {
    boolean save(BeerBoxOrder boxOrder);

    BeerBoxOrder getByOrderNo(String orderNo);

    boolean updateStatus(long beerBoxOrderId, OrderStatusEnum status);

    List<BeerBoxOrder> list(BoxOrderListParam param);

    boolean update(BeerBoxOrder boxOrder);

    List<BeerBoxOrder> listByBeerBoxId(Long blindBoxId, BeerBoxOrderStatus...statuses);

    void removeByOrderNo(String orderNo);
}
