package cn.px.zly.module.cont.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.cont.dao.IContContentAndTagDAO;
import cn.px.zly.module.cont.entity.ContContentAndTagPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class ContContentAndTagDAO extends BaseDAOImpl<ContContentAndTagDAO.ContContentAndTagMapper, ContContentAndTagPO> implements IContContentAndTagDAO {

    @Override
    public List<ContContentAndTagPO> listByContentId(Long contentId) {
        QueryWrapper<ContContentAndTagPO> qw = new QueryWrapper<>();
        qw.lambda().eq(ContContentAndTagPO::getContentId,contentId);
        return list(qw);
    }

    @Mapper
    @Component
    interface ContContentAndTagMapper extends BaseMapper<ContContentAndTagPO>{}
}
