package cn.px.zly.app.module.box.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.px.core.common.exeption.BusinessException;
import cn.px.core.spring.SpringContext;
import cn.px.core.util.DateFormatUtil;
import cn.px.core.util.DateUtil;
import cn.px.core.util.StringUtil;
import cn.px.zly.module.beer.dto.BeerInfoDTO;
import cn.px.zly.module.beer.service.impl.BeerManageService;
import cn.px.zly.module.box.entity.BeerBlindBox;
import cn.px.zly.module.box.entity.BeerBoxDetail;
import cn.px.zly.module.box.service.impl.BeerBoxDetailService;
import cn.px.zly.module.price.entity.PriceManager;
import cn.px.zly.module.price.ienum.ProductTypeEnum;
import cn.px.zly.module.price.service.impl.PriceManagerServer;
import cn.px.zly.module.product.entity.ProductPO;
import cn.px.zly.module.product.entity.SkuPO;
import cn.px.zly.module.product.service.impl.ProductService;
import cn.px.zly.module.product.service.impl.SkuService;
import cn.px.zly.module.subject.entity.SubjectPO;
import cn.px.zly.module.subject.service.impl.SubjectService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel
public class BeerBoxDetailVO {
    @ApiModelProperty(value = "盲盒id")
    private long boxId;

    @ApiModelProperty(value = "盲盒详情id")
    private long boxDetailId;

    @ApiModelProperty(value = "详情背景")
    private String detailBg;

    @ApiModelProperty(value = "背景色")
    private String bgColor;

    @ApiModelProperty(value = "盲盒名")
    private String boxName;

    @ApiModelProperty(value = "盲盒价格")
    private String price;

    @ApiModelProperty("当前盲盒期数")
    private String boxNo;

    @ApiModelProperty("详情标题")
    private String title;

    @ApiModelProperty("详情")
    private String details;

    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    @ApiModelProperty("截止时间")
    private Date cutOffTime;

    @ApiModelProperty("精酿列表")
    private List<BoxBeerVO> beerList = Collections.EMPTY_LIST;

    @ApiModelProperty("价格列表")
    private List<PriceVO> priceList;

    @ApiModelProperty("订阅说明列表")
    private List<String> desList;

    public static BeerBoxDetailVO valueOf(BeerBlindBox box){
        if(box == null){
            return null;
        }
        BeerBoxDetailVO vo = new BeerBoxDetailVO();
        Long boxId = box.getBlindBoxId();
        vo.setBoxId(boxId);
        vo.setBoxName(box.getBoxName());
        BeerBoxDetailService detailService = SpringContext.getBean(BeerBoxDetailService.class);
        BeerBoxDetail detail = detailService.getById(box.getBoxDetailId());
        vo.setTitle(detail.getTitle());
        vo.setDetails(detail.getDetails());
        Date parse = DateFormatUtil.parse(box.getCurrBoxNo(), DateFormatUtil.Pattern.YYYYMM);
        String format = DateFormatUtil.format(parse, DateFormatUtil.Pattern.CN_YYYY_MM);
        vo.setBoxNo(format);
        vo.setBoxDetailId(detail.getBoxDetailId());
        SubjectService subjectService = SpringContext.getBean(SubjectService.class);
        SubjectPO subjectPO = subjectService.getById(box.getSubId());
        vo.setDetailBg(subjectPO.getDetailBg());
        vo.setBgColor(subjectPO.getSubColor());
        //价格，取默认价格
        PriceManagerServer priceServer = SpringContext.getBean(PriceManagerServer.class);
        PriceManager price = priceServer.getDefaultPrice(boxId, ProductTypeEnum.BeerBlindBox);
        DecimalFormat df = new DecimalFormat("#0.00");
        vo.setPrice(df.format(price.getPrice()));
        List<PriceManager> list = priceServer.list(boxId,ProductTypeEnum.BeerBlindBox);
        if(!list.isEmpty()){
            List<PriceVO> priceList = list.stream().map(v -> {
                return PriceVO.valueOf(v);
            }).collect(Collectors.toList());
            vo.setPriceList(priceList);
        }

        Date firstDateOfMonth = DateUtil.getFirstDateOfMonth(parse);
        Date cutOffTime = DateUtil.plusDays(firstDateOfMonth, box.getCutOffTime() - 1);
        vo.setCutOffTime(cutOffTime);

        //订阅说明
        String des = detail.getDes();
        List<String> desList = StringUtil.spilt(des, ";");
        vo.setDesList(desList);

        //精酿列表
        String skuIds = detail.getSkuIds();
        if(StringUtil.isNotBlank(skuIds)){
            SkuService skuService = SpringContext.getBean(SkuService.class);
            BeerManageService beerManageService = SpringContext.getBean(BeerManageService.class);
            List<Long> skuIdList = StringUtil.spilt(skuIds, ";", Long.class);
            List<BoxBeerVO> beerList = new ArrayList<>();
            for (Long skuId : skuIdList) {
                SkuPO skuPO = skuService.getById(skuId);
                if(skuPO != null){
                    ProductService productService = SpringContext.getBean(ProductService.class);
                    ProductPO product = productService.get(skuPO.getProductId());
                    Long beerId = skuPO.getTargetId();
                    BeerInfoDTO beerInfo = beerManageService.queryBeerInfo(beerId);
                    PriceManager beerPrice = priceServer.getDefaultPrice(skuId,ProductTypeEnum.Product);
                    if(beerPrice == null){
                        List<PriceManager> skuPriceList = priceServer.list(skuId, ProductTypeEnum.Product);
                        if(skuPriceList.isEmpty()){
                            throw new BusinessException("商品获取异常，请重试或联系管理员");
                        }
                        beerPrice = skuPriceList.get(0);
                    }
                    BoxBeerVO beerVO = BoxBeerVO.valueOf(beerId,beerInfo,df.format(beerPrice.getPrice()),skuPO.getProductId());
                    beerVO.setBeerImg(product.getCoverImgUrl());
                    beerList.add(beerVO);
                }
            }
            vo.setBeerList(beerList);
        }
        return vo;
    }
}
