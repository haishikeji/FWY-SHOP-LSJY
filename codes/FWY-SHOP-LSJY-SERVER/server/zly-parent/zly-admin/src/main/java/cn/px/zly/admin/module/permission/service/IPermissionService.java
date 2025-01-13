package cn.px.zly.admin.module.permission.service;

import cn.px.zly.admin.module.permission.entity.PermissionPO;
import cn.px.zly.admin.module.permission.vo.ModulePermissionVO;
import cn.px.zly.admin.module.permission.vo.PrivilegeSettingVO;
import cn.px.zly.admin.module.permission.vo.UserPrivilegeVO;

import java.util.List;

/**
 * <p>
 * 用户权限明细 服务类
 * </p>
 *
 */
public interface IPermissionService {

    boolean setPrivilege(PrivilegeSettingVO settingVO);

    UserPrivilegeVO findPrivilege(Integer userId);

    List<ModulePermissionVO> findPermission(Integer userId, String username);

    List<PermissionPO> getRolePrivilege(int roleId);
}
