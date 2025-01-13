package cn.px.zly.module.index.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum BannerTypeEnum implements BaseEnum {
    Index(1,"首页"),
    StoreList(2,"门店列表");

    private int num;
    private String name;
}
