package cn.px.zly.module.box.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.box.dao.IBeerBoxDetailDAO;
import cn.px.zly.module.box.entity.BeerBoxDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class BeerBoxDetailDAO extends BaseDAOImpl<BeerBoxDetailDAO.BeerBoxDetailMapper, BeerBoxDetail> implements IBeerBoxDetailDAO {

    @Override
    public BeerBoxDetail getNewDetail(Long boxId) {
        QueryWrapper<BeerBoxDetail> qw = new QueryWrapper<>();
        qw.lambda().eq(BeerBoxDetail::getBoxId,boxId)
                    .orderByDesc(BeerBoxDetail::getGmtCreate)
                    .last("LIMIT 1");
        return getOne(qw);
    }

    @Override
    public BeerBoxDetail getByBoxIdAndBoxNo(long boxId, String boxNo) {
        QueryWrapper<BeerBoxDetail> qw = new QueryWrapper<>();
        qw.lambda().eq(BeerBoxDetail::getBoxId,boxId)
                    .eq(BeerBoxDetail::getBoxNo,boxNo);
        return getOne(qw);
    }

    @Mapper
    @Component
    interface BeerBoxDetailMapper extends BaseMapper<BeerBoxDetail> {
    }
}
