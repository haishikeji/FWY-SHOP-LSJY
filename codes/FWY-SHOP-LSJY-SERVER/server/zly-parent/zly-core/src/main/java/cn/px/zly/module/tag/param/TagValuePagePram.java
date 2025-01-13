package cn.px.zly.module.tag.param;

import cn.px.zly.base.BasePageParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class TagValuePagePram extends BasePageParam {
    @ApiModelProperty("标签id")
    private Long tagId;
}
