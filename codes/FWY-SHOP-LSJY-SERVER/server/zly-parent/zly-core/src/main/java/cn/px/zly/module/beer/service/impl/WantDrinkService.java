package cn.px.zly.module.beer.service.impl;

import cn.px.zly.module.beer.dao.IWantDrinkDAO;
import cn.px.zly.module.beer.entity.WantDrinkPO;
import cn.px.zly.module.beer.service.IWantDrinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class WantDrinkService implements IWantDrinkService {

    @Autowired
    private IWantDrinkDAO wantDrinkDAO;


    @Override
    public boolean isWantDrink(Long userId, Long beerId) {
        if(userId == null || beerId == null){
            return false;
        }
        return wantDrinkDAO.isWantDrink(userId,beerId);
    }

    @Override
    public int queryCount(Long beerId) {
        if(beerId == null){
            return 0;
        }
        return wantDrinkDAO.queryCount(beerId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean wantDrink(Long userId, Long beerId) {
        if(beerId != null && userId != null){
            WantDrinkPO po = new WantDrinkPO();
            po.setUserId(userId);
            po.setBeerId(beerId);
            po.setCreateTime(new Date());
            return wantDrinkDAO.save(po);
        }
        return false;
    }
}
