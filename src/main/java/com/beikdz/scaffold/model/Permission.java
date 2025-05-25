package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 权限实体类
 * 用于定义系统中的权限信息，支持树形结构的权限管理
 */
@Data
@TableName("permission")
public class Permission {
    /** 权限ID，自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 父权限ID，用于构建权限树结构 */
    private Long parentId;

    /** 权限编码，唯一标识，用于程序中的权限判断 */
    private String code;

    /** 权限名称，用于显示 */
    private String name;

    /** 权限类型，如：menu-菜单、button-按钮、api-接口等 */
    private String type;

    /** 租户ID，用于多租户系统 */
    private Long tenantId;

    /** 权限状态：1-正常，0-禁用 */
    private Integer status;

    /** 权限描述或备注信息 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除标志：0-正常，1-已删除 */
    private Integer deleted;
}
