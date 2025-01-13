package cn.px.zly.module.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.base.ienum.BaseStatusEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ToString
@TableName("product")
public class ProductPO implements BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private String englishName;

    private Long type;

    /**
     * 简要描述,卖点等
     */
    private String des;

    /**
     * 商品封面图
     */
    private String coverImgUrl;

    /**
     * 截取的详情图列表 使用";"拼接
     */
    private String imgUrls;

    private BaseStatusEnum status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 微盟商品id
     */
    private Long weimobGoodsId;

}
