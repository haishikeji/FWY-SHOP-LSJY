package cn.px.zly.module.order.param;

import cn.px.zly.module.order.ienum.OrderTypeEnum;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TradeOrderParam {

    @ApiParam(value="用户编号", hidden = true)
    private long userId;

    @NotBlank(message = "设备唯一码为空")
    @ApiParam("设备唯一码")
    private String deviceNo;

    @NotNull(message = "订单类型为空")
    @ApiParam("订单类型, 见OrderTypeEnum")
    private OrderTypeEnum type;

    @ApiParam("商品数量")
    private int num;

    @ApiParam("用户地址id")
    private long userAddressId;

    @ApiParam("关联外部编号, 订阅盲盒传入priceId")
    private long outId;

}
