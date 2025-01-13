package cn.px.zly.module.msg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.module.msg.ienum.MessageActionEnum;
import cn.px.zly.module.msg.ienum.MessageSrcTypeEnum;
import cn.px.zly.module.msg.ienum.MessageStatusEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("message")
public class Message implements BaseEntity {

    /**
     * 消息编号
     */
    @TableId(value = "message_id", type = IdType.AUTO)
    private long messageId;
    /**
     * 内容来源类型
     */
    private MessageSrcTypeEnum srcType;
    /**
     * 内容来源id
     */
    private Long srcId;
    /**
     * 动作(点赞，评论...)
     */
    private MessageActionEnum action;
    /**
     * 消息归属用户
     */
    private Long userId;
    /**
     * 发送消息的用户
     */
    private Long senderId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 跳转地址
     */
    private String jumpPath;

    private MessageStatusEnum status;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
