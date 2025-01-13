package cn.px.zly.app.module.tag.vo;

import cn.px.zly.module.tag.entity.TagDictionaryPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("标签")
public class TagVO {

    private Long tagId;
    private Long tagValueId;
    @ApiModelProperty("标签名")
    private String name;

    public static TagVO valueOf(TagDictionaryPO po){
        TagVO vo = new TagVO();
        vo.setTagValueId(po.getId());
        vo.setName(po.getName());
        return vo;
    }
}
