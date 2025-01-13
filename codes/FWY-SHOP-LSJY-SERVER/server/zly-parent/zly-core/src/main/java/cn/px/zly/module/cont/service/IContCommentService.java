package cn.px.zly.module.cont.service;

import cn.px.zly.module.cont.entity.ContCommentPO;
import cn.px.zly.module.cont.ienum.CommentAuditStatusEnum;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */

public interface IContCommentService {
    int countByContentId(Long contentId);

    void updateLikeCount(Long likeUserId, long commentId, int i);

    List<ContCommentPO> pageListByContentId(Long contentId, Long lastCommentId, Long hotCommentId);

    ContCommentPO get(Long parentCommentId);

    long save(ContCommentPO commentPO);

    boolean del(long commentId);

    void auditComment(Long commentId, CommentAuditStatusEnum status, String auditDesc);

    /**
     * 获取点赞最多的评论
     * @param contentId
     * @return
     */
    ContCommentPO getHotComment(Long contentId);

    boolean updateByPO(ContCommentPO comment);

    void delByUserId(Long userId);
}
