package cn.px.zly.app.module.account;

import cn.px.core.common.result.JsonResult;
import cn.px.core.config.CoreConfig;
import cn.px.core.security.*;
import cn.px.core.validator.Phone;
import cn.px.core.validator.SmsCode;
import cn.px.zly.module.account.service.IAccountLoginService;
import cn.px.zly.module.account.service.IAccountService;
import cn.px.zly.module.account.service.ISmsService;
import cn.px.zly.module.user.entity.UserPO;
import cn.px.zly.module.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Api(tags = "帐号管理")
@Validated
@RestController
@RequestMapping("/account/security")
public class AccountSecurityController {

    @Autowired
    private ISmsService smsService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IAccountLoginService accountLoginService;

    @Authentication
    @ApiOperation("发送验证码到用户绑定的手机号")
    @PostMapping("/sms/send/v1")
    public JsonResult<String> sendSmsCode(
            @UserLogin UserLoginInfo userLoginInfo,
            @UserAgent UserAgentInfo userAgentInfo,
            @NotBlank(message="手机号")@RequestParam String phone,
            @NotBlank(message="短信票据为空") @RequestParam String ticket) {
        UserPO user = userService.getUser(userLoginInfo.getUserId());
        String realPhone = user.getPhone();
        if(!realPhone.equals(phone)){
            return JsonResult.fail("手机号错误");
        }
        String smsCode = smsService.sendSmsCode(phone, userAgentInfo.getIp(), ticket);
        return JsonResult.ok(smsCode);
    }

    @Authentication
    @ApiOperation("原手机验证码检验")
    @PostMapping("/sms/verify/v1")
    public JsonResult<Object> verifySmsCode(
            @UserLogin UserLoginInfo userLoginInfo,
            @UserAgent UserAgentInfo userAgentInfo,
            @NotBlank(message="手机号为空") @Phone @RequestParam String phone,
            @NotBlank(message="验证码为空") @SmsCode @RequestParam String smsCode) {
        long userId = userLoginInfo.getUserId();
        boolean ret = smsService.checkSmsCode(phone, smsCode);
        if(!ret){
            return JsonResult.fail("验证码错误");
        }
        Map<String, String> map = new HashMap<>();
        String ticket = accountService.generateTicket(userId);
        map.put("changePhoneTicket",ticket);
        return JsonResult.ok(map);
    }

    @Authentication
    @ApiOperation("修改手机号")
    @PostMapping("/phone/update/v1")
    public JsonResult<Object> changePhoneBySmsCode(
            HttpServletRequest request,
            @UserLogin UserLoginInfo userLoginInfo,
            @UserAgent UserAgentInfo userAgentInfo,
            @NotBlank(message="手机号为空") @Phone @RequestParam String phone,
            @NotBlank(message="验证码为空") @SmsCode @RequestParam String smsCode,
            @NotBlank(message="修改手机号票据") @RequestParam String changePhoneTicket) {
        long userId = userLoginInfo.getUserId();
        boolean checkSmsCode = smsService.checkSmsCode(phone, smsCode);
        if(!checkSmsCode){
            return JsonResult.fail("验证码错误");
        }
        boolean ret = accountService.changePhone(userId, phone, changePhoneTicket);
        if(!ret){
            return JsonResult.fail("修改手机号码失败");
        }
        //退出登录
        String token = request.getHeader(CoreConfig.SECURITY_HEADER_TOKEN);
        accountLoginService.logout(token);
        return JsonResult.ok();
    }




}
