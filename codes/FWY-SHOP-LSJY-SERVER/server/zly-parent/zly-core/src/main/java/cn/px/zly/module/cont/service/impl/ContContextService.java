package cn.px.zly.module.cont.service.impl;

import cn.px.core.util.DateUtil;
import cn.px.zly.module.cont.dto.ContScoreDTO;
import cn.px.zly.module.cont.service.IContContentService;
import cn.px.zly.module.cont.service.IContContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class ContContextService implements IContContextService {

    @Autowired
    private IContContentService contContentService;

    @Override
    @Cacheable(value = "zly_CONT_SCORE_LIST",key = "#root.methodName")
    public List<ContScoreDTO> contScorelist() {
        List<ContScoreDTO> scoreList = contContentService.contScoreList();
        //a*圈子评论数/系统所有评论数+b*点赞数/系统点赞数+c*圈子浏览数/系统所有圈子的浏览数+d*圈子时间间隔/系统所有时间间隔
        double a = 10;
        double b = 10;
        double c = 5;
        double d = 40;
        if(scoreList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Date minDate = DateUtil.plusDays(DateUtil.now(), -30);
        int totalCommentCount = 0;
        int totalLikeCount = 0;
        int totalViewCount = 0;
        int totalDayInterval = 0;
        for(ContScoreDTO dto : scoreList) {
            totalCommentCount += dto.getCommentCount();
            totalLikeCount += dto.getLikeCount();
            totalViewCount += dto.getViewCount();
            totalDayInterval += Math.max(0, DateUtil.daysCompare(minDate, dto.getCreateTime()));
        }
        for(ContScoreDTO dto : scoreList) {
            dto.setCommentScore(100.0*dto.getCommentScore()/totalCommentCount);
            dto.setLikeScore(100.0*dto.getLikeScore()/totalLikeCount);
            dto.setViewScore(100.0*dto.getViewCount()/totalViewCount);
            dto.setDayIntervalScore(100.0*Math.max(0, DateUtil.daysCompare(minDate, dto.getCreateTime()))/totalDayInterval);

            double score = a*dto.getCommentCount()/totalCommentCount
                    + b*dto.getLikeCount()/totalLikeCount
                    + c*dto.getViewCount()/totalViewCount
                    + d*Math.max(0, DateUtil.daysCompare(minDate, dto.getCreateTime()))/totalDayInterval;
            dto.setScore(score);
        }
        Collections.sort(scoreList, Comparator.comparingDouble(ContScoreDTO::getScore).reversed());
        if(scoreList.size()>15) {
            List<ContScoreDTO> topList = scoreList.subList(0, 15);
            Collections.shuffle(topList);

            List<ContScoreDTO> restList = scoreList.subList(15, scoreList.size());

            scoreList = new ArrayList<>();
            scoreList.addAll(topList);
            scoreList.addAll(restList);
        }
        return scoreList;
    }

    @Override
    @CacheEvict(value = "zly_CONT_SCORE_LIST",allEntries = true)
    public void scoreListCacheEvict() {
    }

}
