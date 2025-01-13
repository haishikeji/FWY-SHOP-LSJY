package cn.px.zly.module.user.service;

import cn.px.module.account.enums.WechatTypeEnum;
import cn.px.zly.module.user.entity.UserPO;
import cn.px.zly.module.user.param.UserSaveParam;
import cn.px.zly.module.user.param.UserUpdateParam;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IUserService {

    UserPO getUser(Long userId);

    void saveUser(UserSaveParam userSaveParam);

    boolean updateUser(UserUpdateParam param);

    boolean updatePhone(Long userId, String phone);

    /**
     * 注销
     * @param userId
     * @return
     */
    boolean logoff(long userId);

//    void changeOpenid(long accountId, String openid);

    List<UserPO> list();

    String getUserOpenid(long userId, WechatTypeEnum type);
}
