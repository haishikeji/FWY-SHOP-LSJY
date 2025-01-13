package cn.px.zly.module.user.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum implements BaseEnum {

    Normal(1,"正常"),
    Logoff(2,"已注销"),
    BlackList(3,"黑名单")
    ;

    private int num;
    private String name;
}
