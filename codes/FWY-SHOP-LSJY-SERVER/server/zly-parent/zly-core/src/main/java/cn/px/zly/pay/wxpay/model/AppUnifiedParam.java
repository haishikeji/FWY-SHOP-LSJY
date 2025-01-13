package cn.px.zly.pay.wxpay.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class AppUnifiedParam {
    /** 商品描述 说明：商品描述 */
    private String description;
    /** 商户订单号 说明：商户订单号 */
    private String outTradeNo;
    private String attach;
    /** 总金额 分*/
    private int totalFee;
    /** 【用户终端IP】 用户的客户端IP，支持IPv4和IPv6两种格式的IP地址。 */
    private String payerClientIp;
}
