package cn.px.zly.module.beer.service.impl;

import cn.px.core.util.StringUtil;
import cn.px.zly.module.beer.dao.IBeerManageDAO;
import cn.px.zly.module.beer.dto.BeerInfoDTO;
import cn.px.zly.module.beer.entity.BeerManagePO;
import cn.px.zly.module.beer.entity.BeerMaterialPO;
import cn.px.zly.module.beer.entity.BeerPropertiesPO;
import cn.px.zly.module.beer.entity.BeerTypeManagePO;
import cn.px.zly.module.beer.service.IBeerManageService;
import cn.px.zly.module.beer.service.IBeerMaterialService;
import cn.px.zly.module.beer.service.IBeerPropertiesService;
import cn.px.zly.module.beer.service.IBeerTypeManageService;
import cn.px.zly.module.brewery.entity.BreweryPO;
import cn.px.zly.module.brewery.service.IBreweryService;
import cn.px.zly.module.dishes.entity.DishesManagePO;
import cn.px.zly.module.dishes.ienum.DishesTypeEnum;
import cn.px.zly.module.dishes.service.IDishesManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class BeerManageService implements IBeerManageService {

    @Autowired
    private IBeerManageDAO beerManageDAO;

    @Autowired
    private IBreweryService breweryService;

    @Autowired
    private IBeerTypeManageService typeManageService;

    @Autowired
    private IBeerPropertiesService beerPropertiesService;

    @Autowired
    private IBeerMaterialService beerMaterialService;

    @Autowired
    private IDishesManageService dishesManageService;

    @Override
    public List<BeerManagePO> listByTypeId(Long beerTypeId) {
        List<BeerManagePO> beerManagePOS = beerManageDAO.listByTypeId(beerTypeId);
        return beerManagePOS;
    }

    @Override
    public List<BeerManagePO> listByTypeIdAndBreweryId(Long beerTypeId, Long breweryId) {
        if(beerTypeId == null && breweryId == null){
            return Collections.EMPTY_LIST;
        }
        List<BeerManagePO> beerManagePOS = beerManageDAO.listByTypeIdAndBreweryId(beerTypeId,breweryId);
        return beerManagePOS;
    }

    @Override
    public BeerInfoDTO queryBeerInfo(Long beerId) {
        BeerManagePO po = beerManageDAO.getById(beerId);
        if(po == null){
            log.error("queryBeerInfo error:beerId{} does not exist ",beerId);
            return null;
        }
        BeerInfoDTO dto = new BeerInfoDTO();
        dto.setIntroduce(po.getIntroduce());
        dto.setBeerName(po.getOriginalName());
        DishesManagePO dishesManagePO = dishesManageService.queryByTargetId(beerId, DishesTypeEnum.Beer);
        if(dishesManagePO != null){
            dto.setBeerEnName(dishesManagePO.getEnglishName());
        }
        if(po.getFactoryId() != null){
            BreweryPO breweryPO = breweryService.getById(po.getFactoryId());
            dto.setBrewery(breweryPO == null ? "" : breweryPO.getName());
            dto.setPlace(breweryPO == null ? "" : breweryPO.getPlace());
            dto.setBreweryLogo(breweryPO.getLogoImg());
            dto.setBreweryId(breweryPO.getId());
        }
        if(po.getTypeId() != null){
            BeerTypeManagePO type = typeManageService.getById(po.getTypeId());
            dto.setTypeName(type == null ? "" : type.getTypeName());
        }
        BeerPropertiesPO beerPropertiesPO = beerPropertiesService.queryProperties(beerId);
        if(beerPropertiesPO != null){
            dto.setAbv(beerPropertiesPO.getAbv());
            dto.setPlato(beerPropertiesPO.getPlato());
            dto.setIbu(beerPropertiesPO.getIbu());
        }
        String materialIdStr = po.getMaterialId();
        if(StringUtil.isNotBlank(materialIdStr)){
            List<Long> materialIdList = StringUtil.spilt(materialIdStr, ";", Long.class);
            List<String> materialList = new ArrayList<>();
            for (Long materialId : materialIdList) {
                BeerMaterialPO materialPO = beerMaterialService.queryById(materialId);
                if(materialPO != null){
                    materialList.add(materialPO.getName());
                }
            }
            dto.setMaterialList(materialList);
        }
        return dto;
    }

    @Override
    public BeerManagePO getById(Long beerId) {
        if(beerId == null){
            return null;
        }
        return beerManageDAO.getById(beerId);
    }

    @Override
    public List<BeerManagePO> listById(List<Long> beerIds) {
        return (List<BeerManagePO>) beerManageDAO.listByIds(beerIds);
    }
}
