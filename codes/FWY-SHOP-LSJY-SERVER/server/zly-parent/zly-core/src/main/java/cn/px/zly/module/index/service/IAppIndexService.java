package cn.px.zly.module.index.service;

import cn.px.zly.module.index.bo.ZlyCraftVo;
import cn.px.zly.module.index.entity.BannerPO;
import cn.px.zly.module.index.ienum.BannerTypeEnum;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IAppIndexService {

    /**
     * 获取banner列表
     * @return
     */
    List<BannerPO> queryBannerlist(BannerTypeEnum bannerType);

    List<ZlyCraftVo> queryCraftList();
}
