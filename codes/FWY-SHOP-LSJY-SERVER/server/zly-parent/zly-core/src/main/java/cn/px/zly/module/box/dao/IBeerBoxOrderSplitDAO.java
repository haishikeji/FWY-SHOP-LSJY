package cn.px.zly.module.box.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.box.entity.BeerBoxOrderSplit;
import cn.px.zly.module.box.ienum.BeerBoxOrderSplitStatus;

import java.util.List;

/**
 * @Entity cn.px.zly.module.box.entity.BeerBoxOrderSplit
 */
public interface IBeerBoxOrderSplitDAO extends IBaseDAO<BeerBoxOrderSplit> {
    List<BeerBoxOrderSplit> listByBoxOrderId(long beerBoxOrderId);

    List<BeerBoxOrderSplit> queryByDeliveryTimeAndStatus(String dateStr, BeerBoxOrderSplitStatus status);
}
