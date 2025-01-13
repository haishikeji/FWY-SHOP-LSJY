package cn.px.zly.admin.base;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import cn.px.zly.admin.module.permission.service.IPermissionService;
import cn.px.zly.admin.module.permission.vo.ModulePermissionVO;
import cn.px.zly.admin.util.AdminUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 集中配置内存缓存
 */
@Component
public class AdminCache {

    @Autowired
    private IPermissionService permissionService;

    /** 验证码 */
    private final static Cache<String, String> MinuteTwo = CacheBuilder.newBuilder()
            .expireAfterWrite(2, TimeUnit.MINUTES)
            .build();

    public static String imgCode(String id) {
        return imgCode(id, null);
    }

    public static String imgCode(String id, String v) {
        if (StringUtils.isNotBlank(v)) {
            MinuteTwo.put(id, v);
            return null;
        } else {
            return MinuteTwo.getIfPresent(id);
        }
    }

    /** 登录用户用于权限校验的权限数据缓存  */
    private final LoadingCache<String, List<ModulePermissionVO>> PERMISSIONS = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<String, List<ModulePermissionVO>>(){

                @Override
                public List<ModulePermissionVO> load(String account) throws Exception {
                    return permissionService.findPermission(
                            AdminUtil.getUser().getUserId(), account);
                }

            });
    public void resetPermission(String account) {
        PERMISSIONS.invalidate(account);
    }
    public List<ModulePermissionVO> getPermission(String account) {
        if (StringUtils.isNotBlank(account)) {
            return PERMISSIONS.getUnchecked(account);
        }
        return Collections.emptyList();
    }

}
