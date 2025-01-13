package cn.px.zly.app.module.address.vo;

import cn.px.zly.module.district.entity.DistrictPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("城市总数据")
public class CityVO implements Serializable {
    @ApiModelProperty("省列表")
    private List<DistrictVO> provinceList;

    @ApiModelProperty("市列表")
    private List<DistrictVO> cityList;

    @ApiModelProperty("区，县列表")
    private List<DistrictVO> districtList;

    public static CityVO valueOf(List<DistrictPO> list){
        CityVO vo = new CityVO();
        ArrayList<DistrictVO> provinceList = new ArrayList<>();
        ArrayList<DistrictVO> cityList = new ArrayList<>();
        ArrayList<DistrictVO> districtList = new ArrayList<>();
        List<DistrictVO> voList = new ArrayList<>();
        if(!voList.isEmpty()){
            for (DistrictPO po : list) {
                DistrictVO v = DistrictVO.valueOf(po);
                switch (po.getType()){
                    case PROVINCE: provinceList.add(v);break;
                    case CITY: cityList.add(v);break;
                    case DISTRICT: districtList.add(v);break;
                }
            }
        }
        vo.setProvinceList(provinceList);
        vo.setCityList(cityList);
        vo.setDistrictList(districtList);
        return vo;
    }
}
