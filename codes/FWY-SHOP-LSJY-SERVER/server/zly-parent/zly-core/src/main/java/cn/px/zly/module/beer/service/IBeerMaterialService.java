package cn.px.zly.module.beer.service;

import cn.px.zly.module.beer.entity.BeerMaterialPO;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBeerMaterialService {
    BeerMaterialPO queryById(Long materialId);
}
