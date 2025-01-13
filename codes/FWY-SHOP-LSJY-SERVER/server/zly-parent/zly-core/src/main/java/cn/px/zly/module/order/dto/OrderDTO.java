package cn.px.zly.module.order.dto;

import cn.px.zly.module.order.entity.OrderPO;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class OrderDTO extends OrderPO {
    private Long userAddressId;
}
