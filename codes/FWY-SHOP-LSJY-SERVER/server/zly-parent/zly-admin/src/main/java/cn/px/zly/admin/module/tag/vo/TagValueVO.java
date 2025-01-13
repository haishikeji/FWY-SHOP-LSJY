package cn.px.zly.admin.module.tag.vo;

import cn.px.core.util.BeanUtil;
import cn.px.zly.module.tag.dto.TagValueDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("标签值模型")
public class TagValueVO {

    @ApiModelProperty("标签id")
    private Long tagId;
    @ApiModelProperty("标签值id")
    private Long tagValueId;
    @ApiModelProperty("标签名")
    private String tagName;
    @ApiModelProperty("标签值")
    private String tagValue;

    public static TagValueVO valueOf(TagValueDTO dto){
        TagValueVO vo = new TagValueVO();
        if(dto != null){
            BeanUtil.copyBean(dto,vo);
        }
        return vo;
    }
}
