package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 角色权限关联实体类
 * 用于维护角色和权限之间的多对多关系
 */
@Data
@TableName("role_permission")
public class RolePermission {
    /** 关联ID，自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色ID */
    private Long roleId;

    /** 权限ID */
    private Long permissionId;

    /** 租户ID，用于多租户系统 */
    private Long tenantId;

    /** 创建时间 */
    private LocalDateTime createTime;
}
