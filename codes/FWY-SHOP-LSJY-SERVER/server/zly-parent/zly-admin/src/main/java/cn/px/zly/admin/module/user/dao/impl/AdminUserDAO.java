package cn.px.zly.admin.module.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.admin.module.user.dao.IAdminUserDAO;
import cn.px.zly.admin.module.user.entity.AdminUserPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class AdminUserDAO extends BaseDAOImpl<AdminUserDAO.AdminUserMapper,AdminUserPO> implements IAdminUserDAO {

    @Override
    public AdminUserPO queryByAccount(String username) {
        QueryWrapper<AdminUserPO> qw = new QueryWrapper<>();
        qw.lambda().eq(AdminUserPO::getAccount,username);
        return super.getOne(qw);
    }

    @Override
    public List<AdminUserPO> queryByGenUid(Integer userId) {
        QueryWrapper<AdminUserPO> qw = new QueryWrapper<>();
        qw.lambda().eq(AdminUserPO::getGenUid,userId);
        return super.list(qw);
    }

    @Mapper
    @Component
    interface AdminUserMapper extends BaseMapper<AdminUserPO>{

    }
}
