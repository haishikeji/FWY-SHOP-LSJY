package cn.px.zly.module.weimob.cache.impl;

import cn.px.core.cache.BaseCache;
import cn.px.core.cache.CacheDefinition;
import cn.px.core.cache.CachePrefix;
import cn.px.zly.module.weimob.cache.WeimobCacheManager;
import cn.px.zly.module.weimob.dto.TokenDTO;
import cn.px.zly.properties.ZlyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Component
public class WeimobCacheManagerImpl implements WeimobCacheManager {

    @Autowired
    private WeimobCache weimobCache;
    @Autowired
    private ZlyProperties zlyProperties;

    @Override
    public TokenDTO getWeimobAccessToken(){
        TokenDTO accessToken = weimobCache.get(zlyProperties.getWeimob().getClientId());
        return accessToken;
    }

    @Override
    public void putWeimobAccessToken(TokenDTO accessToken){
        weimobCache.set(zlyProperties.getWeimob().getClientId(),accessToken);
    }

    @Component
    static class WeimobCache extends BaseCache {
        @Override
        public CacheDefinition getDefinition() {
            return new CacheDefinition() {

                @Override
                public CachePrefix getPrefix() {
                    return ()->"token_weimob";
                }

                @Override
                public long getTimeout() {
                    return 2;
                }

                @Override
                public TimeUnit getUnit() {
                    return TimeUnit.HOURS;
                }
            };
        }
    }
}
