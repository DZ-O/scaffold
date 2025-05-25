package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户行为日志白名单实体类
 * 用于配置不需要记录日志的用户、操作或URI
 */
@Data
@TableName("user_action_log_white")
public class UserActionLogWhite {
    /** 白名单ID，自增主键 */
    private Long id;

    /**
     * 白名单类型
     * 可选值：user-用户白名单、action-操作白名单、uri-URI白名单
     */
    private String type;

    /** 白名单值，根据type存储对应的用户名、操作类型或URI */
    private String value;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;
}
