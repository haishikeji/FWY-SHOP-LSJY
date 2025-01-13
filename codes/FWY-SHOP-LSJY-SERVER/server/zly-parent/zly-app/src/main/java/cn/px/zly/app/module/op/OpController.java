package cn.px.zly.app.module.op;

import cn.px.core.common.result.JsonResult;
import cn.px.core.security.Authentication;
import cn.px.core.security.UserLogin;
import cn.px.core.security.UserLoginInfo;
import cn.px.zly.module.op.param.OpRecordParam;
import cn.px.zly.module.op.service.IOpRecordService;
import cn.px.zly.module.report.param.ReportParam;
import cn.px.zly.module.report.service.IReportServie;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Api("操作域-用户操作接口")
@Validated
@RestController
@RequestMapping("/op")
public class OpController {

    @Autowired
    private IOpRecordService opRecordService;

    @Autowired
    private IReportServie reportServie;


    @ApiOperation("1、用户操作-用户点赞")
    @Authentication
    @RequestMapping(value="/like", method=RequestMethod.POST)
    public JsonResult<Void> like(
            @UserLogin UserLoginInfo loginInfo,
            @Validated OpRecordParam recordParam) {
        Long userId = loginInfo.getUserId();
        int c = opRecordService.countRecord(userId, recordParam.getRecordType(), recordParam.getOutBusiId());
        if(c>0) {
            return JsonResult.fail("已点赞");
        }
        recordParam.setUserId(userId);
        opRecordService.saveRecord(recordParam);
        return JsonResult.ok();
    }

    @ApiOperation("2。用户操作-取消点赞")
    @Authentication
    @RequestMapping(value = "/like/cancel",method = RequestMethod.POST)
    public JsonResult<Void> unLike(
            @UserLogin UserLoginInfo loginInfo,
            @Validated OpRecordParam recordParam){
        Long userId = loginInfo.getUserId();
        int c = opRecordService.countRecord(userId, recordParam.getRecordType(), recordParam.getOutBusiId());
        if(c == 0) {
            return JsonResult.fail("未点赞");
        }
        recordParam.setUserId(userId);
        opRecordService.unLike(recordParam);
        return JsonResult.ok();
    }

    @ApiOperation("3。用户操作-举报")
    @Authentication
    @RequestMapping(value = "/report",method = RequestMethod.POST)
    public JsonResult<Void> report(@UserLogin UserLoginInfo userLoginInfo,
                                   @Validated ReportParam reportParam){
        long userId = userLoginInfo.getUserId();
        reportParam.setUserId(userId);
        boolean ret =reportServie.report(reportParam);
        if(!ret){
            return JsonResult.fail("举报失败，请稍后重试");
        }
        return JsonResult.ok();
    }

}
