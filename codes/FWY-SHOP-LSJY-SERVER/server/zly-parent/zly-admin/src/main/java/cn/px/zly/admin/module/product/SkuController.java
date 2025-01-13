//package cn.px.zly.admin.module.product;
//
//import com.alibaba.fastjson.JSON;
//import cn.px.core.common.result.JsonResult;
//import cn.px.zly.admin.base.AUri;
//import cn.px.zly.admin.module.product.vo.SkuVO;
//import cn.px.zly.module.brewery.param.AddParam;
//import cn.px.zly.module.product.dto.SkuPriceDTO;
//import cn.px.zly.module.product.param.AddSkuParam;
//import cn.px.zly.module.product.param.UpdateSkuParam;
//import cn.px.zly.module.product.service.ISkuService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.spring.web.json.Json;
//
//import javax.validation.constraints.NotNull;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author 品讯科技
// * @date 2024-08
// */
//@Api(tags = "商品相关--商品单品管理")
//@Log4j2
//@Validated
//@RestController
//public class SkuController {
//
//    @Autowired
//    private ISkuService skuService;
//
//    @ApiOperation("根据商品id获取单品列表")
//    @GetMapping(AUri.SKU_LIST)
//    public JsonResult<List<SkuVO>> queryByProductId(@ApiParam(value = "商品id",required = true)
//                                              @NotNull(message = "商品id不能为空")
//                                              @RequestParam
//                                              Long productId){
//        List<SkuPriceDTO> skuPriceDTOS = skuService.querySkuPriceDTO(productId);
//        if(skuPriceDTOS.isEmpty()){
//            return JsonResult.ok(Collections.EMPTY_LIST);
//        }
//        List<SkuVO> ret = skuPriceDTOS.stream().map(v -> {
//            return SkuVO.valueOf(v);
//        }).collect(Collectors.toList());
//        return JsonResult.ok(ret);
//    }
//
//    @ApiOperation("为商品增加销售规格和对应的价格")
//    @PostMapping(AUri.SKU_ADD)
//    public JsonResult<Object> add(AddSkuParam param){
//        Boolean ret = skuService.addSku(param);
//        return JsonResult.ok();
//    }
//
//    @ApiOperation("更新单品规格价格")
//    @PostMapping(AUri.SKU_UPDATE)
//    public JsonResult<Object> update(UpdateSkuParam param){
//        Boolean ret = skuService.updateByParam(param);
//        return JsonResult.ok();
//    }
//
//    @ApiOperation("删除商品单品规格与价格")
//    @PostMapping(AUri.SKU_DELETE)
//    public JsonResult<Object> deleteSku(@ApiParam(value = "商品单品id",required = true)
//                                        @NotNull(message = "商品单品id不能为空")
//                                        @RequestParam Long skuId){
//        Boolean ret = skuService.deleteSkuById(skuId);
//        return JsonResult.ok();
//    }
//
//
//}
