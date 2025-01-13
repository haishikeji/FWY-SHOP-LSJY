package cn.px.zly.app.module.msg.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("我的消息页面模型")
public class MyMsgVO {
    @ApiModelProperty("官方小助手消息")
    private MessageVO sysMsg;

    @ApiModelProperty("官方小助手未读消息数")
    private int unreadSysMsgCount;

    @ApiModelProperty("点赞消息")
    private FullMessageVO likeMsg;

    @ApiModelProperty("点赞消息未读消息数")
    private int unreadLikeMsgCount;

    @ApiModelProperty("评论消息")
    private FullMessageVO commentMsg;

    @ApiModelProperty("评论消息未读消息数")
    private int unreadCommentMsgCount;
}
