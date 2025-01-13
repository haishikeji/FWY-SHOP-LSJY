package cn.px.zly.module.index.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.base.ienum.BaseStatusEnum;
import cn.px.zly.module.index.dao.IBannerDAO;
import cn.px.zly.module.index.entity.BannerPO;
import cn.px.zly.module.index.ienum.BannerTypeEnum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class BannerDAO extends BaseDAOImpl<BannerDAO.BannerMapper, BannerPO> implements IBannerDAO{

    @Override
    public List<BannerPO> queryBannerlist(BannerTypeEnum bannerType) {
        QueryWrapper<BannerPO> qw = new QueryWrapper<>();
        qw.lambda().eq(BannerPO::getStatus, BaseStatusEnum.NORMAL)
                    .eq(BannerPO::getType,bannerType);
        return super.list(qw);
    }

    @Mapper
    @Component
    interface BannerMapper extends BaseMapper<BannerPO>{

    }
}
