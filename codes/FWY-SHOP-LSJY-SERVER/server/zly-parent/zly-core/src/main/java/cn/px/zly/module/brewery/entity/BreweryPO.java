package cn.px.zly.module.brewery.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.px.core.common.entity.BaseEntity;
import cn.px.zly.module.brewery.ienum.BreweryStatusEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 酒厂表
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@ToString
@TableName("factory_manage")
public class BreweryPO implements BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private String place;

    private String introduce;

    private String logoImg;

    private BreweryStatusEnum status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
