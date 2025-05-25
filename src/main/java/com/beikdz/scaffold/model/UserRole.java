package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户角色关联实体类
 * 用于维护用户和角色之间的多对多关系
 */
@Data
@TableName("user_role")
public class UserRole {
    /** 关联ID，自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

    /** 租户ID，用于多租户系统 */
    private Long tenantId;

    /** 创建时间 */
    private LocalDateTime createTime;
}
