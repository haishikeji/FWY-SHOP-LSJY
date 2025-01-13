package cn.px.zly.admin.module.permission.vo;

import java.util.Map;

/**
 * 权限配置的值 对象
 */
public class PrivilegeSettingVO {

    private int userId = 0; // 角色配置权限时，此值等于0
    private int roleId = 0; // 用户通过角色配置权限时，此值 大于 0
    private Map<String, String> privileges;   // 用户通过角色配置权限时, 此值是空的。 key=模块值， value=节点值|节点值2
    private boolean reset = false; // 清空权限

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Map<String, String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Map<String, String> privileges) {
        this.privileges = privileges;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }
}
