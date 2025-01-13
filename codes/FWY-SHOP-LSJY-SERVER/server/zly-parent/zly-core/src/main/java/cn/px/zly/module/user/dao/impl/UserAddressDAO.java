package cn.px.zly.module.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.cont.ienum.CommentStatusEnum;
import cn.px.zly.module.user.dao.IUserAddressDAO;
import cn.px.zly.module.user.entity.UserAddressPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class UserAddressDAO extends BaseDAOImpl<UserAddressDAO.UserAddressMapper, UserAddressPO> implements IUserAddressDAO  {

    @Override
    public List<UserAddressPO> listByUserId(long userId) {
        QueryWrapper<UserAddressPO> qw = new QueryWrapper<>();
        qw.lambda().eq(UserAddressPO::getUserId,userId)
                    .eq(UserAddressPO::getStatus, CommentStatusEnum.Using)
                    .orderByDesc(UserAddressPO::isDefaultAddress,UserAddressPO::getUpdateTime);
        return list(qw);
    }

    @Override
    public void updateDefaultAddress(Long userId, boolean b) {
        LambdaUpdateWrapper<UserAddressPO> uw = Wrappers.<UserAddressPO>lambdaUpdate();
        uw.eq(UserAddressPO::getUserId,userId)
                .set(UserAddressPO::isDefaultAddress,b);
        update(uw);
    }

    @Component
    @Mapper
    interface UserAddressMapper extends BaseMapper<UserAddressPO>{
    }
}
