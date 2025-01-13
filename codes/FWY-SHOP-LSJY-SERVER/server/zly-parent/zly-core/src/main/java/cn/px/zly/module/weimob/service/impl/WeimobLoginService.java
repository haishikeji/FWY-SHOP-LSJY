package cn.px.zly.module.weimob.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import cn.px.zly.module.weimob.WeimobHttpClient;
import cn.px.zly.module.weimob.constant.WeimobConstant;
import cn.px.zly.module.weimob.dto.LoginDTO;
import cn.px.zly.module.weimob.dto.WeimobDTO;
import cn.px.zly.module.weimob.param.LoginReq;
import cn.px.zly.module.weimob.service.IWeimobLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class WeimobLoginService implements IWeimobLoginService {

    @Autowired
    private WeimobHttpClient weimobHttpClient;

    @Override
    public LoginDTO login(LoginReq loginReq) {
        return weimobHttpClient.request(WeimobConstant.LOGIN_URL,
                loginReq, new TypeReference<WeimobDTO<LoginDTO>>(){});
    }
}
