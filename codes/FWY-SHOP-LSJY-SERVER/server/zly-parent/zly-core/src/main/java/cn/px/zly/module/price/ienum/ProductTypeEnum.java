package cn.px.zly.module.price.ienum;

import cn.px.core.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Getter
@AllArgsConstructor
public enum ProductTypeEnum implements BaseEnum {
    BeerBlindBox(0,"啤酒盲盒"),
    Product(1,"商品");

    private int num;
    private String name;

}
