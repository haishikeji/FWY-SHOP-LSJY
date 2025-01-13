package cn.px.zly.admin.module.user.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.admin.module.user.entity.AdminUserPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IAdminUserDAO extends IBaseDAO<AdminUserPO> {
    AdminUserPO queryByAccount(String username);

    List<AdminUserPO> queryByGenUid(Integer userId);
}
