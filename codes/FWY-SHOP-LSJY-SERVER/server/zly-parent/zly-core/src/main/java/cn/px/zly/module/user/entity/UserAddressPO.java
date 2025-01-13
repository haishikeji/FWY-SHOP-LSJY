package cn.px.zly.module.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.zly.module.cont.ienum.CommentStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 *
 * CREATE TABLE `zly_stores`.`user_address`  (
 *         `user_address_id` bigint(20) NOT NULL AUTO_INCREMENT,
 *         `user_id` bigint(20) NOT NULL COMMENT '用户id',
 *         `name` varchar(20) NOT NULL COMMENT '收货人名称',
 *         `phone` varchar(25) NOT NULL COMMENT '收货人手机号',
 *         `district_id` bigint(20) NOT NULL COMMENT '地区id',
 *         `detail_address` varchar(255) NOT NULL COMMENT '详细地址',
 *         `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否默认',
 *         `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
 *         `update_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
 *         PRIMARY KEY (`user_address_id`)
 * ) ENGINE = InnoDB AUTO_INCREMENT = 10001000;
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Data
@TableName("user_address")
public class UserAddressPO {
    @TableId(value = "user_address_id",type = IdType.AUTO)
    private Long userAddressId;

    private Long userId;

    private String name;

    private String phone;

    private Long districtId;

    private String detailAddress;

    private boolean defaultAddress;

    private CommentStatusEnum status;

    private Date createTime;

    private Date updateTime;

}
