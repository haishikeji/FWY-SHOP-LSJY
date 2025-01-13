package cn.px.zly.module.logoff.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.module.logoff.ienum.LogoffStatusEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ToString
@TableName("logoff")
public class LogoffPO implements BaseEntity {
    @TableId(value = "logoff_id",type = IdType.AUTO)
    private Long logoffId;

    private Long userId;

    private LogoffStatusEnum status;

    private String des;

    private Long optSysUser;

    private Date lastOptTime;

    private Date createTime;

    private Date passTime;

}
