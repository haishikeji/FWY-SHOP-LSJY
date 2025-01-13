package cn.px.zly.module.account.service;

import cn.px.core.common.enums.GenderEnum;
import cn.px.core.security.UserAgentInfo;
import cn.px.module.account.enums.RegisterTypeEnum;
import cn.px.zly.module.account.entity.AccountPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IAccountService {

    AccountPO getAccountByPhone(String phone);

    long registerByPhone(UserAgentInfo userAgentInfo,
                         RegisterTypeEnum registerType, String phone,
                         String nickName, String headImg, GenderEnum gender,
                         String province, String city, String openid);

    String generateTicket(Long accountId);

    boolean changePhone(Long userId,
                     String phone,
                     String changePhoneTicket);

    List<String> queryOpenId(long accountId);
}
