package cn.px.zly.module.tag.dto;

import cn.px.zly.module.tag.entity.TagDictionaryPO;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class TagPageDTO {
    private Long tagId;

    private String tagName;

    private String sn;

    public static TagPageDTO valueOf(TagDictionaryPO po){
        TagPageDTO dto = new TagPageDTO();
        dto.setTagId(po.getId());
        dto.setTagName(po.getName());
        dto.setSn(po.getSn());
        return dto;
    }
}
