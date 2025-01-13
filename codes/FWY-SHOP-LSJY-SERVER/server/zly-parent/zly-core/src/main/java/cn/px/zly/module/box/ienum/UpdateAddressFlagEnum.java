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
public enum UpdateAddressFlagEnum implements BaseEnum {
    On(0,"开启用户修改地址功能"),
    Off(1,"关闭用户修改地址功能");

    private int num;
    private String name;

    @Override
    public String toString(){
        return num+":"+name;
    }
}
