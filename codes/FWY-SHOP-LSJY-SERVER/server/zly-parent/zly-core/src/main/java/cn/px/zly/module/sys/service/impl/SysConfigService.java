package cn.px.zly.module.sys.service.impl;

import cn.px.zly.module.sys.dao.ISysConfigDAO;
import cn.px.zly.module.sys.entity.SysConfigPO;
import cn.px.zly.module.sys.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class SysConfigService implements ISysConfigService {

    private static final String ANDROID_MIN_VERSION = "zly_android_min_version";
    private static final String IOS_MIN_VERSION = "zly_ios_min_version";
    private static final String ANDROID_VERSION = "zly_android_version";
    private static final String IOS_VERSION = "zly_ios_version";
    private static final String ANDROID_UPDATE_URL = "zly_android_update_url";
    private static final String IOS_UPDATE_URL = "zly_ios_version";
    private static final String DISTRICT_CONTROLLER = "district_controller";

    @Autowired
    private ISysConfigDAO sysConfigDAO;


    @Override
    public Integer queryAndroidMinVersion() {
        SysConfigPO po = sysConfigDAO.queryByName(ANDROID_MIN_VERSION);
        return Integer.valueOf(po.getValue());
    }

    @Override
    public Integer queryIosMinVersion() {
        SysConfigPO po = sysConfigDAO.queryByName(IOS_MIN_VERSION);
        return Integer.valueOf(po.getValue());
    }

    @Override
    public SysConfigPO queryAndroidVersion() {
        return sysConfigDAO.queryByName(ANDROID_VERSION);
    }

    @Override
    public SysConfigPO queryIosVersion() {
        return sysConfigDAO.queryByName(IOS_VERSION);
    }

    @Override
    public SysConfigPO queryIosUpdateUrl() {
        return sysConfigDAO.queryByName(IOS_UPDATE_URL);
    }

    @Override
    public SysConfigPO queryAndroidUpdateUrl() {
        return sysConfigDAO.queryByName(ANDROID_UPDATE_URL);
    }

    @Override
    public long queryDistrictVersion() {
        SysConfigPO sysConfigPO = sysConfigDAO.queryByName(DISTRICT_CONTROLLER);
        if(sysConfigPO != null){
            return Long.valueOf(sysConfigPO.getValue());
        }
        return 0l;
    }

    public static void main(String[] args) {
        System.out.println(Long.valueOf("202305231049"));
    }
}
