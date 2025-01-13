package cn.px.zly.base.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum BaseStatusEnum implements BaseEnum{
    NORMAL(1,"上架"),
    DISABLE(2,"下架");

    private int num;
    private String name;

    @Override
    public int getNum() {
        return this.num;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
