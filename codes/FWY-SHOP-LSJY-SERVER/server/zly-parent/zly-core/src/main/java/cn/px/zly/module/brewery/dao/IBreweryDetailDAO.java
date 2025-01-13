package cn.px.zly.module.brewery.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.brewery.entity.BreweryDetailPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBreweryDetailDAO extends IBaseDAO<BreweryDetailPO> {
    List<BreweryDetailPO> queryListByIds(List<Long> idList);
}
