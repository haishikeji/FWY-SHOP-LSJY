package cn.px.zly.app.module.index.vo;

import cn.px.zly.module.index.entity.BannerPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("banner图")
public class BannerVO {

    @ApiModelProperty
    private Long bannerId;

    @ApiModelProperty("banner图地址")
    private String imgUrl;

    @ApiModelProperty("跳转地址")
    private String jumpUrl;

    @ApiModelProperty("banner图主色")
    private String bgColor;

    public static List<BannerVO> listValueOf(List<BannerPO> bannerPOList){
        if(bannerPOList == null || bannerPOList.isEmpty()){
            return Collections.emptyList();
        }
        List<BannerVO> list = new ArrayList<>();
        for (BannerPO po : bannerPOList) {
            BannerVO vo = new BannerVO();
            vo.setBannerId(po.getId());
            vo.setImgUrl(po.getImgUrl());
            vo.setJumpUrl(po.getJumpUrl());
            vo.setBgColor(po.getBgColor());
            list.add(vo);
        }
        return list;
    }

}
