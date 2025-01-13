package cn.px.zly.app.module.user.vo;

import com.jthinking.common.util.ip.IPInfo;
import com.jthinking.common.util.ip.IPInfoUtils;
import cn.px.core.common.enums.GenderEnum;
import cn.px.core.util.StringUtil;
import cn.px.zly.module.user.entity.UserPO;
import cn.px.zly.util.PhoneUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("用户简单模型")
public class UserSimpleVO {
    @ApiModelProperty("用户id")
    private long userId;

    @ApiModelProperty("头像")
    private String headImg;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("phone")
    private String phone;

    @ApiModelProperty("性别 ")
    private GenderEnum gender;

    @ApiModelProperty("ip地址")
    private String ipAddress = "";

    @ApiModelProperty("未读消息数")
    private int unreadMsgNum;

    public static UserSimpleVO valueOf(UserPO po,String ipStr){
        if(po == null){
            return null;
        }
        UserSimpleVO vo = new UserSimpleVO();
        vo.setUserId(po.getUserId());
        vo.setHeadImg(po.getHeadImg());
        vo.setNickName(po.getNickName());
        String phone = po.getPhone();
        if(StringUtil.isNotBlank(phone)){
            vo.setPhone(PhoneUtils.blurPhone(phone));
        }

        vo.setGender(po.getGender());
        if(StringUtil.isNotBlank(ipStr)){
            IPInfo provinceCity = IPInfoUtils.getIpInfo(ipStr);
            if(provinceCity != null){
                vo.setIpAddress(provinceCity.getCountry()+" "+provinceCity.getProvince());
            }
        }
        return vo;
    }

    public static UserSimpleVO valueOf(UserPO po,String ipStr,int unreadMsg){
        UserSimpleVO vo = valueOf(po, ipStr);
        vo.setUnreadMsgNum(unreadMsg);
        return vo;
    }
}
