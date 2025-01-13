package cn.px.zly.module.brewery.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.brewery.entity.BreweryPO;
import cn.px.zly.module.brewery.param.UpdateParam;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBreweryDAO extends IBaseDAO<BreweryPO> {
    Boolean updateByParam(UpdateParam param);

    List<BreweryPO> queryPageByLastId(Long lastBreweryId);

    List<BreweryPO> queryShelvesPage(Long lastBreweryId);
}
