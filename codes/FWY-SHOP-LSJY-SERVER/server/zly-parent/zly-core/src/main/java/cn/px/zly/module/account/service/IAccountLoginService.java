package cn.px.zly.module.account.service;

import cn.px.core.security.UserAgentInfo;
import cn.px.module.account.entity.AccountThirdWechat;
import cn.px.zly.module.account.dto.AccountLoginDTO;
import cn.px.zly.module.account.entity.AccountLogin;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IAccountLoginService {

    String verifyPhoneNumber(UserAgentInfo userAgentInfo, String token);

    AccountLoginDTO exchangeMobileV2(UserAgentInfo userAgentInfo, String token);

    AccountLoginDTO loginBySms(UserAgentInfo userAgentInfo, String phone, String smsCode);

    /**
     * 苹果ID登录
     * @param userAgentInfo 用户客户端信息
     * @param userID 授权的用户唯一标识
     * @param email 授权的用户资料
     * @param authorizationCode 授权code
     * @param identityToken 苹授权用户的JWT凭证
     * @return  登录响应
     */
    AccountLoginDTO loginByApple(UserAgentInfo userAgentInfo, String userID,
                                 String email, String authorizationCode, String identityToken);

    /**
     * 苹果登录绑定(一键登录)
     * @param userAgentInfo 用户客户端信息
     * @param userID 授权的用户唯一标识
     * @param email 授权的用户资料
     * @param authorizationCode 授权code
     * @param identityToken 苹授权用户的JWT凭证
     * @param token 一键登录token
     * @return 登录响应
     */
    AccountLoginDTO bindAppleByPhone(UserAgentInfo userAgentInfo, String userID, String email,
                                     String authorizationCode, String identityToken, String token);

    /**
     * 苹果登录绑定(手机短信)
     * @param userAgentInfo 用户客户端信息
     * @param userID 授权的用户唯一标识
     * @param email 授权的用户资料
     * @param authorizationCode 授权code
     * @param identityToken 苹授权用户的JWT凭证
     * @param phone 手机号码
     * @param smsCode 短信验证码
     * @return 登录响应
     */
    AccountLoginDTO bindAppleBySms(UserAgentInfo userAgentInfo, String userID, String email,
                                   String authorizationCode, String identityToken, String phone,
                                   String smsCode);

    /**
     * 自动登录(刷新token)
     * @param userAgentInfo 用户客户端信息
     * @param token 上次登录token
     * @return 登录响应
     */
    AccountLoginDTO loginByAuto(UserAgentInfo userAgentInfo, String token);

    /**
     * 登录登录
     * @param token 登录token
     */
    void logout(String token);

    /**
     * 微信登录
     * @param userAgentInfo 用户客户端信息
     * @param code 微信code
     * @return 登录响应
     */
    AccountLoginDTO loginByWechat(UserAgentInfo userAgentInfo, String code);
    /**
     * 微信登录绑定(手机验证码)
     * @param userAgentInfo 用户客户端信息
     * @param code 微信code
     * @param phone 手机号码
     * @param smsCode 短信验证码
     * @return 登录响应
     */
    AccountLoginDTO bindWechatBySms(UserAgentInfo userAgentInfo, String code,
                                    String phone, String smsCode);
    /**
     * 微信登录绑定(一键登录)
     * @param userAgentInfo 用户客户端信息
     * @param code 微信code
     * @param token 一键登录token
     * @return 登录响应
     */
    AccountLoginDTO bindWechatByPhone(UserAgentInfo userAgentInfo, String code,
                                      String token);

    /**
     * 微信小程序code置换session，并且更新保存到数据库
     * @param userAgentInfo
     * @param code
     * @param phoneCode
     * @return
     */
    AccountLoginDTO wechatAppletCode2Session(UserAgentInfo userAgentInfo, String code, String phoneCode);

    /**
     * 通过 手机号获取凭证（phoneCode）获取用户手机号码 https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/user-info/phone-number/getPhoneNumber.html
     * 再对用户账号进行绑定
     * @param userAgentInfo
     * @param code
     * @param phoneCode
     * @return
     */
    AccountLoginDTO wechatAppletBindPhone(UserAgentInfo userAgentInfo,
                                          String code,
                                          String phoneCode);

    /**
     * 获取某个用户最后一次登录的信息
     */
    AccountLogin getLastLogin(long accountId);

    void updateAccountWechat(AccountThirdWechat wechat);
}
