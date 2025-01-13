package cn.px.zly.admin.module.permission.service;

import cn.px.core.common.result.ServiceResult;
import cn.px.zly.admin.module.permission.entity.AdminRolePO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface IAdminRoleService {

    List<AdminRolePO> findAll();

    AdminRolePO findRoleName(Integer roleId);

    ServiceResult<Boolean> add(AdminRolePO rolePO);

    ServiceResult<Boolean> updateRole(AdminRolePO rolePO);
}
