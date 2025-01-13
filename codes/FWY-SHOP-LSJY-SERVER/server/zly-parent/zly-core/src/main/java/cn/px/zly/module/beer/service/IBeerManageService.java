package cn.px.zly.module.beer.service;

import cn.px.zly.module.beer.dto.BeerInfoDTO;
import cn.px.zly.module.beer.entity.BeerManagePO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBeerManageService {
    List<BeerManagePO> listByTypeId(Long beerTypeId);

    List<BeerManagePO> listByTypeIdAndBreweryId(Long beerTypeId, Long breweryId);

    BeerInfoDTO queryBeerInfo(Long beerId);

    BeerManagePO getById(Long beerId);

    List<BeerManagePO> listById(List<Long> beerIds);
}
