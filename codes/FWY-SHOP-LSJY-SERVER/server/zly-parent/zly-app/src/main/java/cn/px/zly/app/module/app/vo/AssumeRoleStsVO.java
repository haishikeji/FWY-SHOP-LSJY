package cn.px.zly.app.module.app.vo;

import cn.px.core.util.BeanUtil;
import cn.px.manager.third.aliyun.sts.model.AssumeRoleSts;
import lombok.Data;

@Data
public class AssumeRoleStsVO {

    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private String expiration;

    public static AssumeRoleStsVO of(AssumeRoleSts assumeRoleSts) {
        AssumeRoleStsVO assumeRoleStsVO = new AssumeRoleStsVO();
        BeanUtil.copyBean(assumeRoleSts, assumeRoleStsVO);
        return assumeRoleStsVO;
    }
}
