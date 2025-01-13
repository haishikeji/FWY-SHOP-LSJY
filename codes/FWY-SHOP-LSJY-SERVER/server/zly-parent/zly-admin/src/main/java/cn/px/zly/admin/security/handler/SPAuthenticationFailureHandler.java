package cn.px.zly.admin.security.handler;

import cn.px.core.common.result.JsonResult;
import cn.px.zly.admin.exception.ValidateCodeException;
import cn.px.zly.util.HttpHelper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理器
 */
@Component
public class SPAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String message;

        if (exception instanceof ValidateCodeException) {
            message = exception.getMessage();
        } else {
            message = "登录认证失败，请联系网站管理员！";
        }
        HttpHelper.ok(JsonResult.fail(message), response);
    }
}
