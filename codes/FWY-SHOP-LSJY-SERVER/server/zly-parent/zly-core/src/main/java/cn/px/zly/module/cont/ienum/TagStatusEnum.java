package cn.px.zly.module.cont.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum TagStatusEnum  implements BaseEnum {

    Valid(1, "有效"),
    Disable(2, "禁用"),
    ;

    private int num;
    private String name;
}
