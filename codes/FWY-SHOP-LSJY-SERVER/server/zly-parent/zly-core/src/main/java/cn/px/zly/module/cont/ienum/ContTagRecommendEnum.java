package cn.px.zly.module.cont.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContTagRecommendEnum implements BaseEnum {

    Yes(1, "是"),
    No(2, "否")
    ;
    private int num;
    private String name;
}
