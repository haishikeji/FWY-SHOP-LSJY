package cn.px.zly.module.weimob.dao.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.weimob.dao.IWeimobTokenDAO;
import cn.px.zly.module.weimob.entity.WeimobTokenPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class WeimobTokenDAO extends BaseDAOImpl<WeimobTokenDAO.WeimobTokenMapper,WeimobTokenPO> implements IWeimobTokenDAO {

    @Mapper
    @Component
    interface WeimobTokenMapper extends BaseMapper<WeimobTokenPO> {
    }
}
