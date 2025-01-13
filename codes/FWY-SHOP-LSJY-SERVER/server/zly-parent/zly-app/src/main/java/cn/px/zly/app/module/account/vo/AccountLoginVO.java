package cn.px.zly.app.module.account.vo;

import cn.px.core.common.enums.GenderEnum;
import cn.px.core.util.BeanUtil;
import cn.px.zly.module.account.dto.AccountLoginDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("用户登录模型")
public class AccountLoginVO implements Serializable {

    @ApiModelProperty
    private long accountId;

    @ApiModelProperty(value="昵称")
    private String nickName;

    @ApiModelProperty(value="头像")
    private String headImg;

    @ApiModelProperty(value="登录Token")
    private String token;

    @ApiModelProperty(name="gender",value="性别： 0：男 1：女")
    private GenderEnum gender;

    public static AccountLoginVO of(AccountLoginDTO loginDTO) {
        AccountLoginVO loginVO = new AccountLoginVO();
        BeanUtil.copyBean(loginDTO, loginVO);
        return loginVO;
    }
}
