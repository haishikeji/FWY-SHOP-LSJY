package cn.px.zly.admin.module.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_permission")
public class PermissionPO implements BaseEntity {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 生成此权限的用户ID
     */
    private Integer createUid;

    /**
     * 模块名称PModule.pname
     */
    private String moduleName;

    /**
     * 权限信息
     */
    private String privilege;

    /**
     * 子系统标识
     */
    private String plateform;

    /**
     * 更新时间
     */
    private Date updateTime;

}
