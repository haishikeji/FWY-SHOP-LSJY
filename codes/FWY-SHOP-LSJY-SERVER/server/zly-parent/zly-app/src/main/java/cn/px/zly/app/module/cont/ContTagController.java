package cn.px.zly.app.module.cont;

import cn.px.core.common.result.JsonResult;
import cn.px.core.security.Authentication;
import cn.px.core.security.UserLogin;
import cn.px.core.security.UserLoginInfo;
import cn.px.zly.app.module.cont.vo.ContTagVO;
import cn.px.zly.module.cont.entity.ContTagPO;
import cn.px.zly.module.cont.param.ContTagParam;
import cn.px.zly.module.cont.service.IContTagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Api(tags = "圈子")
@Log4j2
@Validated
@RestController
@RequestMapping("/cont/tag")
public class ContTagController {

    @Autowired
    private IContTagService contTagService;

    @Authentication
    @ApiOperation("推荐标签列表")
    @GetMapping("/list/recommend")
    public JsonResult<List<ContTagVO>> recommendList(@UserLogin UserLoginInfo userLoginInfo){
        List<ContTagPO> poList = contTagService.recommendList();
        if(poList.isEmpty()){
            return JsonResult.ok(Collections.EMPTY_LIST);
        }
        List<ContTagVO> result = poList.stream().map(po -> {
            return ContTagVO.valueOf(po);
        }).collect(Collectors.toList());
        return JsonResult.ok(result);
    }

    @Authentication
    @ApiOperation("新增标签")
    @PostMapping("/save")
    public JsonResult<Long> saveTag(@UserLogin UserLoginInfo userLoginInfo,
                                    @Validated ContTagParam param){
        long contTagId = contTagService.saveTag(param.getTagName());
        return JsonResult.ok(contTagId);
    }


}
