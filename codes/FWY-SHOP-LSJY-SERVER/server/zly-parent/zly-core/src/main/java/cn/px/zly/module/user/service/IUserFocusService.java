package cn.px.zly.module.user.service;

import cn.px.zly.module.user.entity.UserFocusPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IUserFocusService {

    /**
     * 关注
     *
     */
    boolean focusUser(long currentUserId,long focusUserId);

    /**
     * 取消关注
     *
     */
    boolean unFocusUser(long currentUserId,long unFocusUserId);

    /**
     *获取已关注的用户列表
     */
    List<UserFocusPO> focusUserIdList(long currentUserId);

    /*
    *   获取fans的userid列表
    */
    List<UserFocusPO> getFansList(long currentUserId);
}
