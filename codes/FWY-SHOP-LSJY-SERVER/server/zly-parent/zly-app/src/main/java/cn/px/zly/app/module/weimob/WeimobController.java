package cn.px.zly.app.module.weimob;

import cn.px.core.common.result.JsonResult;
import cn.px.core.security.*;
import cn.px.core.spring.EnvironmentContext;
import cn.px.core.util.StringUtil;
import cn.px.zly.app.module.weimob.vo.WeimobLoginVO;
import cn.px.zly.module.user.entity.UserPO;
import cn.px.zly.module.user.service.IUserService;
import cn.px.zly.module.weimob.dto.LoginDTO;
import cn.px.zly.module.weimob.param.LoginReq;
import cn.px.zly.module.weimob.service.IWeimobLoginService;
import cn.px.zly.module.weimob.service.IWeimobTokenService;
import cn.px.zly.properties.ZlyProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URLDecoder;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Log4j2
@Api(tags = "微盟相关")
@Validated
@RestController
@RequestMapping("/weimob")
public class WeimobController {

    @Autowired
    private IWeimobTokenService weimobTokenService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ZlyProperties properties;

    @Autowired
    private IWeimobLoginService weimobLoginService;

    @ApiOperation(value="1、微盟免密码登录", notes="微盟免密码登录,拼接格式indexPage?appTicket={appTicket}&appClientId={clientId}, 如果没有appTicket或clientId，则说明请求失败，客户端自行判断是否进入")
    @PostMapping("/login")
    @Authentication
    public JsonResult<WeimobLoginVO> login(HttpServletRequest request,
                                    @UserLogin UserLoginInfo userLoginInfo,
                                    @UserAgent UserAgentInfo userAgentInfo,
                                    @ApiParam(value="指定重定向地址(不填默认到首页)",  required=false) @RequestParam(required=false) String redirectUrl){
        UserPO user = userService.getUser(userLoginInfo.getUserId());
        String indexPage = properties.getWeimob().getIndexPage();
        String clientId = properties.getWeimob().getClientId();
        if(StringUtil.isBlank(redirectUrl)) {
            redirectUrl = indexPage;
        } else {
            try {
                redirectUrl = URLDecoder.decode(redirectUrl, "UTF-8");
                URI uri = URI.create(redirectUrl);
                if(!uri.getHost().endsWith("weimob.com")) {
                    log.warn("weimob redirectUrl doamin {}, redirect to indexPage", redirectUrl);
                    redirectUrl = indexPage;
                }
            } catch (Exception e) {
                log.warn("weimob redirectUrl doamin {}, redirect to indexPage", redirectUrl);
                redirectUrl = indexPage;
            }
        }

        WeimobLoginVO loginVO = new WeimobLoginVO();
        String openUserId = String.valueOf(user.getUserId());
        if(!EnvironmentContext.isProdEnv()) {
            openUserId = "test_"+openUserId;
        }
        try {
            LoginReq loginReq = new LoginReq();
            loginReq.setOpenUserId(openUserId);
            loginReq.setNickname(user.getNickName());
            loginReq.setHeadurl(user.getHeadImg());
            loginReq.setTelephone(user.getPhone());
            LoginDTO loginResp = weimobLoginService.login(loginReq);
            if(loginResp == null) {
                loginVO.setIndexPage(redirectUrl);
                return JsonResult.ok(loginVO);
            }
            String appTicket = loginResp.getAppTicket();
            loginVO.setIndexPage(redirectUrl);
            loginVO.setAppTicket(appTicket);
            loginVO.setClientId(clientId);
            return JsonResult.ok(loginVO);
        } catch (Exception e) {
            log.error("weimob login error.", e);
            loginVO.setIndexPage(redirectUrl);
            return JsonResult.ok(loginVO);
        }
    }
}
