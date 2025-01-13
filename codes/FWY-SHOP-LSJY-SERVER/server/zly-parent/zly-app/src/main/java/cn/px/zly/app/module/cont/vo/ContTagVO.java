package cn.px.zly.app.module.cont.vo;

import cn.px.zly.module.cont.entity.ContTagPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("标签模型")
public class ContTagVO {

    @ApiModelProperty("标签id")
    private Long contTagId;

    @ApiModelProperty("标签名")
    private String contTagName;

    public static ContTagVO valueOf(ContTagPO po){
        ContTagVO vo = new ContTagVO();
        vo.setContTagId(po.getContTagId());
        vo.setContTagName(po.getTagName());
        return vo;
    }
}
