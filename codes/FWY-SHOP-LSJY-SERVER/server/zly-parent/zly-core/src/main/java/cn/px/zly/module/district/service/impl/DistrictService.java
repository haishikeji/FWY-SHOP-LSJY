package cn.px.zly.module.district.service.impl;

import cn.px.core.util.StringUtil;
import cn.px.zly.module.district.dao.IDistrictDAO;
import cn.px.zly.module.district.entity.DistrictPO;
import cn.px.zly.module.district.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 城市数据
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class DistrictService implements IDistrictService {

    private static final String CACHE_KEY_CITY_LIST = "CACHE_KEY_CITY_LIST";
    private static final String CACHE_KEY_CITY_MAP = "CACHE_KEY_CITY_MAP";

    @Autowired
    private IDistrictDAO districtDAO;

    @Override
    @Cacheable(value = CACHE_KEY_CITY_LIST,key = "#root.methodName")
    public List<DistrictPO> list() {
        return districtDAO.list();
    }

    @Override
    public Map<Long, String> queryMapByIds(List<Long> disIdList) {
        Map<Long, String> cityNameMap = getCityNameMap();
        if(disIdList.isEmpty()){
            return Collections.EMPTY_MAP;
        }
        Map<Long, String> result = new HashMap<>();
        for (Long id : disIdList){
            result.put(id,cityNameMap.get(id));
        }
        return result;
    }

    @Override
    @Cacheable(value = CACHE_KEY_CITY_MAP,key = "#root.methodName")
    public  Map<Long, String> getCityNameMap(){
        List<DistrictPO> list = list();
        Map<Long, String> map = list.stream().collect(Collectors.toMap(DistrictPO::getId, DistrictPO::getDistrictName));

        Map<Long, String> result = new HashMap<>();
        for (DistrictPO po : list) {
            Long id = po.getId();
            List<Long> ids = subDistrictSqeToIdList(po.getDistrictSqe());
            if (ids.isEmpty()) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            for (Long sqeId : ids) {
                String name = map.get(sqeId);
                if(StringUtil.isNotBlank(name)){
                    builder.append(name + " ");
                }
            }
            String name =  builder.toString();
            result.put(id,name);
        }
        return result;
    }

    @Override
    public DistrictPO getById(Long districtId) {
        if(districtId != null){
            DistrictPO po = districtDAO.getById(districtId);
            return po;
        }
        return null;
    }

    @Override
    public String getCompleteAddress(Long districtId, List<DistrictPO> list) {
        list = list == null ? new ArrayList<>() : list;
        StringBuilder sb = null;
        if(districtId == null || districtId.equals(0l) || districtId.equals(1l)){
            sb = new StringBuilder();
            if(!list.isEmpty()){
                for (int i = list.size() - 1; i >= 0; i--) {
                    sb.append(list.get(i).getDistrictName());
                }
            }
            return sb.toString();
        }
        DistrictPO po = getById(districtId);
        if(po != null){
            list.add(po);
            return getCompleteAddress(po.getPid(), list);
        }else {
            sb = new StringBuilder();
            if(!list.isEmpty()){
                for (int i = list.size() - 1; i >= 0; i--) {
                    sb.append(list.get(i).getDistrictName());
                }
            }
        }
        return sb.toString();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void save(DistrictPO po) {
        districtDAO.save(po);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void update(DistrictPO po) {
        districtDAO.updateById(po);
    }

    public List<Long> subDistrictSqeToIdList(String districtSqe){
        if(StringUtil.isBlank(districtSqe)){
            return Collections.EMPTY_LIST;
        }
        String[] split = districtSqe.split("[.]");
        List<Long> result = new ArrayList<>();
        for (String s : split) {
            if (StringUtil.isNotBlank(s)) {
                result.add(StringUtil.str2Long(s));
            }
        }
        return result;
    }
}
