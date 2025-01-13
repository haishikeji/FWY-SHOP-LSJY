package cn.px.zly.module.account.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.account.entity.AccountLogin;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IAccountLoginDAO extends IBaseDAO<AccountLogin> {

    AccountLogin getLastLogin(long accountId);
}
