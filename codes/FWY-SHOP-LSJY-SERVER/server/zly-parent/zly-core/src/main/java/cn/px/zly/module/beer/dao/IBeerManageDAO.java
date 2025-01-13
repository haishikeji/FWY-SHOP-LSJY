package cn.px.zly.module.beer.dao;

import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.beer.entity.BeerManagePO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBeerManageDAO extends IBaseDAO<BeerManagePO> {
    List<BeerManagePO> listByTypeId(Long beerTypeId);

    List<BeerManagePO> listByTypeIdAndBreweryId(Long beerTypeId, Long breweryId);
}
