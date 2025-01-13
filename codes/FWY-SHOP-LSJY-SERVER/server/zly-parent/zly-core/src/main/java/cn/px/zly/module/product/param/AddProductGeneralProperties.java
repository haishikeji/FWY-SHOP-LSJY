package cn.px.zly.module.product.param;

import io.swagger.annotations.ApiModel;
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
@ApiModel("增加一般商品属性")
public class AddProductGeneralProperties {

    @NotNull
    @ApiModelProperty("商品id")
    private Long productId;

    @NotBlank
    @ApiModelProperty("tagValueId,多个之间用;分格")
    private String tagValueId;
}
