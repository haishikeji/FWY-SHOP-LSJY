package cn.px.zly.admin.module.permission.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.admin.module.permission.entity.PermissionPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IPermissionDAO extends IBaseDAO<PermissionPO> {

    boolean removeByUserId(int userId);

    boolean removeByRoleId(int roleId);

    List<PermissionPO> queryListByUserId(int userId);

    List<PermissionPO> listByRoleId(int roleId);
}
