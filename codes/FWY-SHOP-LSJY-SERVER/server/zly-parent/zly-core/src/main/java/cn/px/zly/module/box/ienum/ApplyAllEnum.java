package cn.px.zly.module.box.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum ApplyAllEnum implements BaseEnum {
    only(0,"仅当前业务数据"),
    all(1,"后续所有业务数据");

    private int num;
    private String name;

    @Override
    public String toString() {
        return num+":"+name;
    }
}
