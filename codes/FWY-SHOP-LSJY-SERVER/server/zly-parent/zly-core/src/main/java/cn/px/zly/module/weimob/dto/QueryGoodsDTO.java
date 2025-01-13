package cn.px.zly.module.weimob.dto;

import lombok.Data;

import java.util.List;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
public class QueryGoodsDTO {
    private Integer pageNum;

    private Integer totalCount;

    private List<GoodsListInfo> pageList;
}
