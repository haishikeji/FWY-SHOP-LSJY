package cn.px.zly.module.box.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.box.dao.IBeerBoxOrderSplitDAO;
import cn.px.zly.module.box.entity.BeerBoxOrderSplit;
import cn.px.zly.module.box.ienum.BeerBoxOrderSplitStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class BeerBoxOrderSplitDAO extends BaseDAOImpl<BeerBoxOrderSplitDAO.BeerBoxOrderSplitMapper, BeerBoxOrderSplit> implements IBeerBoxOrderSplitDAO {

    @Override
    public List<BeerBoxOrderSplit> listByBoxOrderId(long beerBoxOrderId) {
        LambdaQueryWrapper<BeerBoxOrderSplit> qw = Wrappers.<BeerBoxOrderSplit>lambdaQuery();
        qw.eq(BeerBoxOrderSplit::getBoxOrderId,beerBoxOrderId)
                .last("ORDER BY field(status,11,5,4,3,2,1),create_time DESC,month ASC");
        return this.list(qw);
    }

    @Override
    public List<BeerBoxOrderSplit> queryByDeliveryTimeAndStatus(String dateStr, BeerBoxOrderSplitStatus status) {
        LambdaQueryWrapper<BeerBoxOrderSplit> qw = Wrappers.<BeerBoxOrderSplit>lambdaQuery();
        qw.eq(BeerBoxOrderSplit::getStatus,status)
                .last("AND CONVERT(delivery_time,DATETIME) like "+"\'"+dateStr+"%\'");
        return this.list(qw);
    }

    @Mapper
    @Component
    interface BeerBoxOrderSplitMapper extends BaseMapper<BeerBoxOrderSplit> {}
}
