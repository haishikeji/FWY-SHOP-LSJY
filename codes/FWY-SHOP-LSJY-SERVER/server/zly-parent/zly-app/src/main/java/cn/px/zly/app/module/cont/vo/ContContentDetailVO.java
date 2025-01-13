package cn.px.zly.app.module.cont.vo;

import cn.px.core.util.BeanUtil;
import cn.px.zly.module.cont.dto.ContContentDTO;
import cn.px.zly.module.cont.dto.ContContentDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collections;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ApiModel("点评模型")
public class ContContentDetailVO extends ContContentDTO{

    @ApiModelProperty("创建时间")
    private String createTime;



    public static ContContentDetailVO valueOf(ContContentDetailDTO dto){
        ContContentDetailVO vo = new ContContentDetailVO();
        ContContentVO contContentVO = ContContentVO.valueOf(dto);
        BeanUtil.copyBean(contContentVO,vo);
        if(vo.getImgUrlList() == null){
            vo.setImgUrlList(Collections.EMPTY_LIST);
        }
        vo.setCreateTime(dto.getCreateTime());
        return vo;
    }
}
