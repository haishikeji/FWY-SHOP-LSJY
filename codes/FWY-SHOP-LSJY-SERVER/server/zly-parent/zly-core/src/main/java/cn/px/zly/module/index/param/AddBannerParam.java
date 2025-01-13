package cn.px.zly.module.index.param;

import cn.px.core.common.jump.support.JumpTypeEnum;
import cn.px.zly.base.ienum.BaseStatusEnum;
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
@ApiModel("新增banner参数")
public class AddBannerParam {

    @NotBlank
    @ApiModelProperty("banner图地址")
    private String img_url;

    @NotNull
    @ApiModelProperty(value = "跳转类型")
    private JumpTypeEnum jumpType;

    @ApiModelProperty(value = "h5/微盟跳转地址",notes = "跳转类型为H5或weimob时传入")
    private String h5Url;

    @ApiModelProperty(value = "安卓跳转参数 跳转类型为app时传入")
    private String targetActivity;

    @ApiModelProperty(value = "安卓跳转参数 跳转类型为app时传入")
    private int tab;

    @ApiModelProperty(value = "ios跳转参数 跳转类型为app时传入")
    private String page;

    @ApiModelProperty(value = "背景色")
    private String bgColor;

    @NotNull
    @ApiModelProperty("上下架状态 0:上架，1:下架")
    private BaseStatusEnum status;
}
