package cn.px.zly.module.brewery.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.base.ienum.BaseStatusEnum;
import cn.px.zly.module.brewery.dao.IBreweryDetailDAO;
import cn.px.zly.module.brewery.entity.BreweryDetailPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class BreweryDetailDAO extends BaseDAOImpl<BreweryDetailDAO.BreweryDetailMapper, BreweryDetailPO> implements IBreweryDetailDAO {


    @Override
    public List<BreweryDetailPO> queryListByIds(List<Long> idList) {
        QueryWrapper<BreweryDetailPO> qw = new QueryWrapper<>();
        qw.lambda().in(BreweryDetailPO::getFactoryId,idList)
                    .eq(BreweryDetailPO::getStatus, BaseStatusEnum.NORMAL);
        return list(qw);
    }

    @Mapper
    @Component
    interface BreweryDetailMapper extends BaseMapper<BreweryDetailPO>{
    }
}
