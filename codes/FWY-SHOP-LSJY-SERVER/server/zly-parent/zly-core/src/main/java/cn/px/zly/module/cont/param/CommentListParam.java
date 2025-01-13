package cn.px.zly.module.cont.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class CommentListParam {

    @Range(message = "contentId不能为空")
    @ApiModelProperty("contentId")
    private Long contentId;

    @ApiModelProperty("上一页最后一条评论的commentId")
    private Long lastCommentId;
}
