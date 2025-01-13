package cn.px.zly.module.district.dao.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.district.dao.IDistrictDAO;
import cn.px.zly.module.district.entity.DistrictPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class DistrictDAO extends BaseDAOImpl<DistrictDAO.DistrictMapper,DistrictPO> implements IDistrictDAO {

    @Mapper
    @Component
    interface DistrictMapper extends BaseMapper<DistrictPO>{
    }
}
