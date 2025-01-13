package cn.px.zly.module.cont.dao;


import cn.px.core.mybatis.plus.IBaseDAO;
import cn.px.zly.module.cont.entity.ContContentAndTagPO;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IContContentAndTagDAO extends IBaseDAO<ContContentAndTagPO> {
    List<ContContentAndTagPO> listByContentId(Long contentId);
}
