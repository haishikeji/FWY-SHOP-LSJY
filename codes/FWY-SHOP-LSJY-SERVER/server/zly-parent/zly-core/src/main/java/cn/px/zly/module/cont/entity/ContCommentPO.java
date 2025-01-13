package cn.px.zly.module.cont.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.module.cont.ienum.CommentAuditStatusEnum;
import cn.px.zly.module.cont.ienum.CommentStatusEnum;
import cn.px.zly.module.cont.ienum.CommentTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("cont_comment")
public class ContCommentPO implements BaseEntity {
	@TableId(value = "comment_id",type = IdType.AUTO)
	private Long commentId;
	private CommentTypeEnum commentType;
	private Long contentId;
	private Long parentCommentId;
	private Long userId;
	private Long atUserId;
	private String comment;
	private Integer likeCount;
	private String ipAddress;
	private CommentStatusEnum status;
	private CommentAuditStatusEnum auditStatus;
	private String auditDesc;
	private Date createTime;
	private Date updateTime;

}
