package cn.px.zly.module.beer.dao.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.beer.dao.IBeerPropertiesDAO;
import cn.px.zly.module.beer.entity.BeerPropertiesPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class BeerPropertiesDAO extends BaseDAOImpl<BeerPropertiesDAO.BeerPropertiesMapper, BeerPropertiesPO> implements IBeerPropertiesDAO {

    @Mapper
    @Component
    interface BeerPropertiesMapper extends BaseMapper<BeerPropertiesPO> {

    }
}
