package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 角色实体类
 * 用于定义系统中的角色信息，作为用户和权限之间的桥梁
 */
@Data
@TableName("role")
public class Role {
    /** 角色ID，自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 角色编码，唯一标识，用于程序中的权限判断 */
    private String code;

    /** 角色名称，用于显示 */
    private String name;

    /** 租户ID，用于多租户系统 */
    private Long tenantId;

    /** 角色状态：1-正常，0-禁用 */
    private Integer status;

    /** 角色描述或备注信息 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除标志：0-正常，1-已删除 */
    private Integer deleted;
}
