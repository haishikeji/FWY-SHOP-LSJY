package cn.px.zly.module.stores.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("stores")
public class StoresPO implements BaseEntity {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String name;

    private String address;

    private Long manager;

    private String lng;

    private String lat;

    private Date businessStartTime;

    private Date businessEndTime;

    private String jumpUrl;

    private BaseStatusEnum status;

    private String groupLiveCode;

    private Date createTime;

    private Date updateTime;
}
