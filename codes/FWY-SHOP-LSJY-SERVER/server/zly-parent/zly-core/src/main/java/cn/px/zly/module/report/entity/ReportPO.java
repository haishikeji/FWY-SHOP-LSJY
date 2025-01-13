package cn.px.zly.module.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.module.report.ienum.ReportStatusEnum;
import cn.px.zly.module.report.ienum.ReportTargetTypeEnum;
import cn.px.zly.module.report.ienum.ReportTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ToString
@TableName("report")
public class ReportPO implements BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long reportId;

    private Long userId;

    private ReportTypeEnum reportType;

    private Long targetId;

    private ReportTargetTypeEnum targetType;

    private String reason;

    private String imgUrls;

    private ReportStatusEnum reportStatus;

    private String auditDesc;

    private Date reportTime;

}
