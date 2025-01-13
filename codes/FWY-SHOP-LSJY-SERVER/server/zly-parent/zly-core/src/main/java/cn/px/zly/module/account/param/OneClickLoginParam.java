package cn.px.zly.module.account.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="登陆校验参数对象",description="用户对象user")
public class OneClickLoginParam {

    public static final String ANDROID = "android";
    public static final String IOS = "ios";

    @ApiModelProperty(value="当前APP对应的appid",name="appId")
    private String appId;
    @ApiModelProperty(value="运营商token",name="accessToken")
    private String accessToken;
    @ApiModelProperty(value="运营商",name="telecom")
    private String telecom;
    @ApiModelProperty(value="UNIX时间戳",name="timestamp")
    private String timestamp;
    @ApiModelProperty(value="随机数",name="randoms")
    private String randoms;
    @ApiModelProperty(value="SDK版本号",name="version")
    private String version;
    @ApiModelProperty(value="设备型号",name="device")
    private String device;
    @ApiModelProperty(value="签名",name="sign")
    private String sign;
    @ApiModelProperty(name="client",value="安卓：android，苹果：ios")
    private String client;
}
