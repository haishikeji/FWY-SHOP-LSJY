package cn.px.zly.app.module.box.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.px.core.spring.SpringContext;
import cn.px.core.util.DateFormatUtil;
import cn.px.core.util.DateUtil;
import cn.px.zly.module.box.entity.BeerBlindBox;
import cn.px.zly.module.subject.entity.SubjectPO;
import cn.px.zly.module.subject.service.impl.SubjectService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class BlindBoxListVO {

    @ApiModelProperty("盲盒id")
    private Long boxId;

    @ApiModelProperty("盲盒名称")
    private String boxName;

    @ApiModelProperty("盲盒背景")
    private String boxBg;

    @ApiModelProperty("当前盲盒期数")
    private String currBoxNo;

    @JsonFormat(pattern = "yyyy/MM/dd",timezone = "GMT+8")
    @ApiModelProperty("截止时间")
    private Date cutOffTime;


    public static BlindBoxListVO valueOf(BeerBlindBox box){
        BlindBoxListVO vo = new BlindBoxListVO();
        Long boxId = box.getBlindBoxId();
        vo.setBoxId(boxId);
        Date parse = DateFormatUtil.parse(box.getCurrBoxNo(), DateFormatUtil.Pattern.YYYYMM);
        String format = DateFormatUtil.format(parse, DateFormatUtil.Pattern.CN_YYYY_MM);
        vo.setCurrBoxNo(format);
        vo.setBoxName(box.getBoxName());
        Date firstDateOfMonth = DateUtil.getFirstDateOfMonth(parse);
        Date cutOffTime = DateUtil.plusDays(firstDateOfMonth, box.getCutOffTime() - 1);
        vo.setCutOffTime(cutOffTime);
        SubjectService subjectService = SpringContext.getBean(SubjectService.class);
        SubjectPO subjectPO = subjectService.getById(box.getSubId());
        vo.setBoxBg(subjectPO.getBg());
        return vo;
    };


}
