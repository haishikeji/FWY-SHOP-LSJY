package cn.px.zly.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@Component
@ConfigurationProperties(prefix = "zly.config")
public class ZlyProperties {
    /**
     * 上传模块配置
     */
    private UploadProperties upload;
    /**
     * 用户模块配置
     */
    private UserProperties user;
    /**
     * 微盟配置
     */
    private WeimobProperties weimob;

    private SfProperties sf;

    @Data
    public static class UploadProperties {
        /**
         * 访问地址前缀
         * urlPrefix+objectName
         */
        private String urlPrefix;
        /**
         * 上传bucket名称
         */
        private String bucketName;
    }

    @Data
    public static class UserProperties {
        /**
         * 用户默认头像
         */
        private String defaultHeadImg;
    }

    @Data
    public static class WeimobProperties{
        /**
         * 微盟clientId
         */
        private String clientId;
        /**
         * 微盟clientSecret
         */
        private String clientSecret;
        /**
         * 店铺首页
         */
        private String indexPage;
        /**
         * weimob.redirectUri=http://120.79.211.173:7100/weimob/login_callback
         * weimob.loginCallbackUrl=http://120.79.211.173:7100/weimob/login_callback
         */
        private String redirectUri;

        private String loginCallbackUrl;
    }

    @Data
    public static class SfProperties{
        //顺分请求地址域名
        private String sfDomain;
        //顾客id
        private String partnerId;
        //校验码
        private String checkWord;
        //password
        private String passWord;
    }
}
