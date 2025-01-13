package cn.px.zly.status;

import cn.px.core.common.status.BaseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum ZlyStatus implements BaseStatus {
    ContNotFund(440,"访问的内容不存在或已下架");

    private final int code;
    private final String msg;

}
