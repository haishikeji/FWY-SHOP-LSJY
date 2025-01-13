package cn.px.zly.module.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.px.core.common.entity.BaseEntity;
import cn.px.module.account.enums.AccountStateEnum;
import cn.px.module.account.enums.RegisterTypeEnum;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("account")
public class AccountPO implements BaseEntity {

    /**
     * 帐号id, 同用户id
     */
    @TableId(value = "account_id", type = IdType.AUTO)
    private long accountId;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 注册类型
     */
    private RegisterTypeEnum registerType;
    /**
     * 客户端os
     */
    private String os;
    /**
     * 下载应用市场
     */
    private String market;
    /**
     * 注册来源
     */
    private String source;
    /**
     * 帐号状态
     */
    private AccountStateEnum state;
    /**
     * 帐号封禁开始时间
     */
    private Date gmtBlockBegin;
    /**
     * 帐号封禁截止时间
     */
    private Date gmtBlockEnd;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;
}
