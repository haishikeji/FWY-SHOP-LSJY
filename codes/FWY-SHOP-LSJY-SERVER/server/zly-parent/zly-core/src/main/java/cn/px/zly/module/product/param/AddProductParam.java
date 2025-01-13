package cn.px.zly.module.product.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@Validated
public class AddProductParam {

    @NotBlank(message = "商品名称不能为空")
    @ApiModelProperty(value = "商品名称",required = true)
    private String name;

    @NotNull(message = "商品简要描述不能为空")
    @ApiModelProperty(value = "商品简要描述",required = true)
    private String des;

    @NotBlank(message = "商品封面图不能为空")
    @ApiModelProperty(value = "商品封面图",required = true)
    private String coverImgUrl;

    @NotNull(message = "微盟商品id不能为空")
    @ApiModelProperty(value = "微盟商品id",required = true)
    private Long weimobGoodsId;
}
