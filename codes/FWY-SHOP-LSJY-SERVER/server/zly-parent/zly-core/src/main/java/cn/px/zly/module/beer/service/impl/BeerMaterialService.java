package cn.px.zly.module.beer.service.impl;

import cn.px.zly.module.beer.dao.IBeerMaterialDAO;
import cn.px.zly.module.beer.entity.BeerMaterialPO;
import cn.px.zly.module.beer.service.IBeerMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class BeerMaterialService implements IBeerMaterialService {

    @Autowired
    private IBeerMaterialDAO beerMaterialDAO;

    @Override
    public BeerMaterialPO queryById(Long materialId) {
        if(materialId == null){
            log.error("query beer material error,materialId IS NULL !");
            return null;
        }
        return beerMaterialDAO.getById(materialId);
    }
}
