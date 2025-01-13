package cn.px.zly.module.brewery.dto;

import cn.px.zly.module.brewery.ienum.BreweryStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class BreweryDTO {

    private Long id;

    private String name;

    private String coverImg;

    private Long districtId;

    private String districtName;

    private String logImg;

    private String bgColor;

    private String detailId;

    private BreweryStatusEnum status;

    private Date createTime;

    private Date updateTime;

    private String content;
}
