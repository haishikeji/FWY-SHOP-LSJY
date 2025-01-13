package cn.px.zly.app.module.address.vo;

import cn.px.core.spring.SpringContext;
import cn.px.core.util.StringUtil;
import cn.px.zly.module.district.entity.DistrictPO;
import cn.px.zly.module.district.service.impl.DistrictService;
import cn.px.zly.module.user.entity.UserAddressPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel
public class AddressDetailVO {
    @ApiModelProperty("收货地址id")
    private Long userAddressId;

    @ApiModelProperty("收货人名称")
    private String name;

    @ApiModelProperty("收货人手机号码")
    private String phone;

    @ApiModelProperty("所在地区")
    private List<DistrictVO> districtList = Collections.EMPTY_LIST;

    @ApiModelProperty("详细地址")
    private String detailAddress;

    @ApiModelProperty("是否为默认地址")
    private boolean defaultAddress;

    public static AddressDetailVO valueOf(UserAddressPO po){
        AddressDetailVO vo = new AddressDetailVO();
        BeanUtils.copyProperties(po,vo);
        DistrictService districtService = SpringContext.getBean(DistrictService.class);
        DistrictPO districtPO = districtService.getById(po.getDistrictId());
        if(districtPO != null){
            String districtSqe = districtPO.getDistrictSqe();
            List<Long> districtIdList = StringUtil.spilt(districtSqe, "\\.", Long.class);
            List<DistrictVO> list = new ArrayList<>();
            for (Long disId : districtIdList) {
                if(disId.equals(1L)){
                    continue;
                }
                list.add(DistrictVO.valueOf(districtService.getById(disId)));
            }
            vo.setDistrictList(list);
//            if(StringUtil.isNotBlank(po.getPhone())){
//                vo.setPhone(PhoneUtils.blurPhone(po.getPhone()));
//            }
        }
        return vo;
    }
}
