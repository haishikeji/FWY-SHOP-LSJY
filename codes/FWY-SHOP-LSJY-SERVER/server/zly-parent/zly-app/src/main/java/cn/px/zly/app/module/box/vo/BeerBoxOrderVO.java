package cn.px.zly.app.module.box.vo;

import cn.px.core.spring.SpringContext;
import cn.px.core.util.DateFormatUtil;
import cn.px.zly.module.box.entity.BeerBlindBox;
import cn.px.zly.module.box.entity.BeerBoxOrder;
import cn.px.zly.module.box.ienum.BeerBoxOrderStatus;
import cn.px.zly.module.box.service.impl.BeerBlindBoxService;
import cn.px.zly.module.subject.entity.SubjectPO;
import cn.px.zly.module.subject.service.impl.SubjectService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel
public class BeerBoxOrderVO {
    @ApiModelProperty("盲盒订单id")
    private Long beerBoxOrderId;

    @ApiModelProperty(value = "啤酒盲盒订单状态",notes = "cn.px.zly.app.apidoc.SwaggerDisplayEnum")
    private BeerBoxOrderStatus boxOrderStatus;

    @ApiModelProperty(value = "盲盒名")
    private String boxName;

    @ApiModelProperty("盲盒logo")
    private String boxLogo;

    @ApiModelProperty("订购月数")
    private int month;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("订购时间")
    private String buyTime;

    @ApiModelProperty("订购信息")
    private List<BoxOrderSplitListVO> orderInfoList;

    public static BeerBoxOrderVO valueOf(BeerBoxOrder beerBoxOrder){
        if(beerBoxOrder == null){
            return null;
        }
        BeerBoxOrderVO vo = new BeerBoxOrderVO();
        vo.setBeerBoxOrderId(beerBoxOrder.getBeerBoxOrderId());
        vo.setBoxOrderStatus(beerBoxOrder.getStatus());
        BeerBlindBoxService beerBlindBoxService = SpringContext.getBean(BeerBlindBoxService.class);
        SubjectService subjectService = SpringContext.getBean(SubjectService.class);
        BeerBlindBox beerBlindBox = beerBlindBoxService.getById(beerBoxOrder.getBeerBoxId());
        SubjectPO subjectPO = subjectService.getById(beerBlindBox.getSubId());
        vo.setBoxName(beerBlindBox.getBoxName());
        vo.setBoxLogo(subjectPO.getSubLogo());
        vo.setBuyTime(DateFormatUtil.format(beerBoxOrder.getGmtCreate(), DateFormatUtil.Pattern.SLASH_YYYY_MM_DD));

        int price = beerBoxOrder.getPrice();
        BigDecimal decimal = new BigDecimal(price);
        decimal = decimal.divide(new BigDecimal(100));
        DecimalFormat df = new DecimalFormat("#0.00");
        vo.setPrice(df.format(decimal));
        vo.setMonth(beerBoxOrder.getMonthNum());
        List<BoxOrderSplitListVO> orderInfoList = BoxOrderSplitListVO.valueOf(beerBoxOrder.getBeerBoxOrderId());
        vo.setOrderInfoList(orderInfoList);
        return vo;
    }


}
