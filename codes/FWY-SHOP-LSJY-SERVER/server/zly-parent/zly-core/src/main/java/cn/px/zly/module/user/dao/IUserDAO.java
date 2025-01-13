package cn.px.zly.module.user.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.user.entity.UserPO;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IUserDAO extends IBaseDAO<UserPO> {

    /**
     * 通过昵称查找用户
     * @param nickName 用户昵称
     * @return 用户信息
     */
    UserPO getByNickName(String nickName);

    /**
     * 更新用户昵称
     * @param userId 用户编号
     * @param nickName 用户昵称
     * @return 是否成功
     */
    boolean updateNickName(long userId, String nickName);
}
