package cn.px.zly.module.account.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import cn.px.core.mybatis.plus.BaseDAOImpl;
import cn.px.zly.module.account.dao.IAccountDAO;
import cn.px.zly.module.account.entity.AccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Repository
public class AccountDAO extends BaseDAOImpl<AccountDAO.AccountMapper, AccountPO> implements IAccountDAO {

    @Override
    public AccountPO getAccountByPhone(String phone) {
        LambdaQueryWrapper<AccountPO> queryWrapper = Wrappers.<AccountPO>lambdaQuery()
                .eq(AccountPO::getPhone, phone);
        return this.getOne(queryWrapper);
    }

    @Mapper
    @Component
    interface AccountMapper extends BaseMapper<AccountPO> {
    }
}
