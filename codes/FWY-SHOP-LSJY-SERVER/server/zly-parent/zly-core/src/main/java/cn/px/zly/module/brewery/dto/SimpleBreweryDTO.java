package cn.px.zly.module.brewery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("简单酒厂模型")
public class SimpleBreweryDTO {
    @ApiModelProperty("酒厂id")
    private Long breweryId;

    @ApiModelProperty("酒厂名")
    private String breaweryName;
}
