package cn.px.zly.module.product.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@Validated
public class AddSkuParam {
    @NotNull(message = "商品id不能为空")
    @ApiModelProperty(value = "商品id",required = true)
    private Long productId;

    @NotBlank(message = "标签值id不能为空")
    @ApiModelProperty(value = "标签值id,多个用;分隔",required = true)
    private String tagValueIds;

    @NotNull(message = "价格不能为空")
    @ApiModelProperty(value = "价格",required = true)
    private BigDecimal price;
}
