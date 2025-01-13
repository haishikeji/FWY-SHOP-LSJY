package cn.px.zly.module.product.dto;

import cn.px.zly.module.beer.dto.BeerInfoDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class ProductDetailDTO {

    private Long productId;

    private List<String> imgUrls;

    @ApiModelProperty("啤酒信息")
    private BeerInfoDTO beerInfo;

    @ApiModelProperty("想喝人数")
    private int wantDrinkNum;

    @ApiModelProperty("点评数")
    private int commentNum;

    @ApiModelProperty("想喝")
    private boolean wantDrink;

    @ApiModelProperty("weimo_goods_id")
    private Long weimobGoodsId;

//    private List<TagValueDTO> tagValueList;
//
//    private List<SkuPriceDTO> SkuPriceDTOList;
}
