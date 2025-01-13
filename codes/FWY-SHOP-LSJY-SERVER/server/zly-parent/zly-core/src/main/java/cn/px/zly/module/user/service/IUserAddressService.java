package cn.px.zly.module.user.service;

import cn.px.zly.module.user.entity.UserAddressPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IUserAddressService {

    UserAddressPO getById(Long userAddressId);

    String getUserAddress(UserAddressPO po);

    List<UserAddressPO> listByUserId(long userId);

    boolean save(UserAddressPO po);

    boolean update(UserAddressPO po);
}
