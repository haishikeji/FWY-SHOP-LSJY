package cn.px.zly.module.order.ienum;

import cn.px.core.common.enums.BaseEnum;
import cn.px.zly.module.box.service.support.BeerBoxOrderHandler;
import cn.px.zly.module.order.service.support.OrderHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单类型
 * @author 品讯科技
 * 2020-10-09 下午7:07
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum implements BaseEnum {

    BeerBlindBox(1, "啤酒盲盒", BeerBoxOrderHandler.class),
    ;
    private int num;
    private String name;
//    private Class<? extends TradeOrderAppleProductIdExtractor> appleProductIdExtractorClass;
    private Class<? extends OrderHandler> handlerClass;

    @Override
    public String toString() {
        return num+":"+name;
    }
}
