package cn.px.zly.admin.module.stores;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.px.core.common.result.JsonResult;
import cn.px.zly.admin.base.AUri;
import cn.px.zly.admin.module.stores.vo.StoresVO;
import cn.px.zly.base.BasePageParam;
import cn.px.zly.module.stores.entity.StoresPO;
import cn.px.zly.module.stores.param.AddStoresParam;
import cn.px.zly.module.stores.param.UpdateStoresParam;
import cn.px.zly.module.stores.service.IStoresService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Validated
@RestController
@Api("基本信息-门店管理")
public class StoresController {

    @Autowired
    private IStoresService storesService;

    @ApiOperation(value = "门店分页列表",response = StoresVO.class)
    @GetMapping(AUri.STORES_LIST)
    public JsonResult<IPage<StoresVO>> storesPageList(BasePageParam param){
        IPage<StoresPO> page = storesService.listPage(param);
        IPage<StoresVO> pageResult = new Page<>();
        pageResult.setSize(page.getSize());
        pageResult.setCurrent(page.getCurrent());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());
        List<StoresPO> poList = page.getRecords();
        List<StoresVO> result = null;
        if(poList == null || poList.isEmpty()){
            result = Collections.EMPTY_LIST;
        }else {
            result = poList.stream().map(v -> {
                return StoresVO.valueOf(v);
            }).collect(Collectors.toList());
        }
        pageResult.setRecords(result);
        return JsonResult.ok(pageResult);
    }

    @ApiOperation("添加新门店")
    @PostMapping(AUri.STORES_ADD)
    public JsonResult<Object> addStores(AddStoresParam param){
        storesService.addStores(param);
        return JsonResult.ok();
    }

    @ApiOperation("修改门店")
    @PostMapping(AUri.STORES_UPDATE)
    public  JsonResult<Object> updateStores(UpdateStoresParam param){
        storesService.update(param);
        return JsonResult.ok();
    }

}
