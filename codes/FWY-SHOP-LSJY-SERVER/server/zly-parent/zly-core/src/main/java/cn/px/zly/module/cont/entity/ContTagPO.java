package cn.px.zly.module.cont.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.module.cont.ienum.ContTagRecommendEnum;
import cn.px.zly.module.cont.ienum.TagAuditStatusEnum;
import cn.px.zly.module.cont.ienum.TagStatusEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ToString
@TableName("cont_tag")
public class ContTagPO implements BaseEntity {
    @TableId(value = "cont_tag_id",type = IdType.AUTO)
    private Long contTagId;

    private String tagName;

    private int sortFlag;

    private ContTagRecommendEnum recommend;

    private TagStatusEnum status;

    private TagAuditStatusEnum auditStatus;

    private String auditDesc;

    private Date createTime;

    private Date updateTime;

}
