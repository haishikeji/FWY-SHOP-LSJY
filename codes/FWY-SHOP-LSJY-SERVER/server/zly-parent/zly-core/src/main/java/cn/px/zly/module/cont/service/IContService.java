package cn.px.zly.module.cont.service;

import cn.px.zly.module.cont.dto.CommentAndLikeDTO;
import cn.px.zly.module.cont.dto.ContCommentDTO;
import cn.px.zly.module.cont.dto.ContContentDTO;
import cn.px.zly.module.cont.dto.ContContentDetailDTO;
import cn.px.zly.module.cont.param.CommentListParam;
import cn.px.zly.module.cont.param.CommentParam;
import cn.px.zly.module.cont.param.ContentListParam;
import cn.px.zly.module.cont.param.PubContentParam;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
public interface IContService {
    List<ContContentDTO> list(ContentListParam param,Long currentUserId);

    ContContentDetailDTO contentDetail(Long contentId, Long currentUserId);

    List<ContCommentDTO> commentList(CommentListParam param, Long currentUserId);

    long comment(CommentParam commentParam);

    long pubReview(PubContentParam param);

    boolean delContent(long userId, long contentId);

    boolean delComment(long userId, long commentId);

    List<ContContentDTO> myContentList(long userId, Long lastContentId, Long beVisitedUserId);

    void deleteByUserId(Long userId);

    CommentAndLikeDTO queryUserLikeAndCommentNum(long userId);
}
