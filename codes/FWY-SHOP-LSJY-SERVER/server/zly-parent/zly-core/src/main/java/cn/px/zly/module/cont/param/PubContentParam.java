package cn.px.zly.module.cont.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class PubContentParam {

    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(hidden = true)
    private String ipStr;

    @Length(min = 1,max = 800,message = "内容字数为1～800字")
    @NotBlank(message = "内容不能为空")
    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("商品id 评价商品时传入")
    private Long productId;

    @NotBlank(message = "气泡图片不能为空")
    @ApiModelProperty("图片,多个图片之间使用;分隔")
    private String imgUrls;

    @ApiModelProperty("标签id，多个id之间使用;分隔")
    private String tagIds;


}
