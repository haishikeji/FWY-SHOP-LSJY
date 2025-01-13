package cn.px.zly.module.logoff.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum LogoffStatusEnum implements BaseEnum {
    Apply(1,"申请中"),
    Cancel(2,"已撤销"),
    Logoff(3,"已注销");

    private int num;
    private String name;

}
