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
public enum LogoffAuditStatusEnum implements BaseEnum {
    Todo(1, "待审核")
    ,Pass(2, "审核通过")
    ,NoPass(3,"审核未通过");

    private int num;
    private String name;
}
