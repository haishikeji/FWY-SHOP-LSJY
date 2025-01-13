package cn.px.zly.admin.module.product.vo;

import cn.px.core.util.BeanUtil;
import cn.px.zly.base.ienum.BaseStatusEnum;
import cn.px.zly.module.product.entity.ProductPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("商品列表")
public class ProductVO {

    @ApiModelProperty("商品id")
    private Long productId;

    @ApiModelProperty("商品标题")
    private String name;

    @ApiModelProperty("商品简要描述")
    private String des;

    @ApiModelProperty("商品封面图")
    private String coverImgUrl;

    @ApiModelProperty("商品上下架状态 0上架,1下架")
    private BaseStatusEnum status;

    @ApiModelProperty("微盟商品id")
    private Long weimobGoodsId;

    public static ProductVO valueOf(ProductPO po){
        ProductVO vo = new ProductVO();
        BeanUtil.copyBean(po,vo);
        vo.setProductId(po.getId());
        return vo;
    }
}
