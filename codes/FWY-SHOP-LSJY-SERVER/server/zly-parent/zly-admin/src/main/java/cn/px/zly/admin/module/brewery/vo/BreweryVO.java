package cn.px.zly.admin.module.brewery.vo;

import cn.px.core.util.BeanUtil;
import cn.px.zly.module.brewery.dto.BreweryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("酿酒厂模型")
public class BreweryVO implements Serializable {
    @ApiModelProperty("酿酒厂id")
    private Long breweryId;
    @ApiModelProperty("酿酒厂名")
    private String name;
    @ApiModelProperty("封面图")
    private String coverImg;
    @ApiModelProperty("城市id")
    private Long districtId;
    @ApiModelProperty("城市名")
    private String districtName;
    @ApiModelProperty("log图")
    private String logImg;
    @ApiModelProperty("log图主色")
    private String bgColor;
    @ApiModelProperty("酒厂详情")
    private String content;

    public static BreweryVO valueOf(BreweryDTO dto){
        BreweryVO vo = new BreweryVO();
        BeanUtil.copyBean(dto,vo);
        vo.setBreweryId(dto.getId());
        return vo;
    }
}
