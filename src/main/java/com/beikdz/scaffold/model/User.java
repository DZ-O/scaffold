package com.beikdz.scaffold.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 用于存储系统用户的基本信息，包括个人资料、认证信息和系统状态等
 */
@Data
@TableName("user")
public class User {
    /** 用户ID，自增主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 用户名，用于登录，唯一标识 */
    private String username;

    /** 用户密码，加密存储 */
    private String password;

    /** 用户昵称，用于显示 */
    private String nickname;

    /** 电子邮箱 */
    private String email;

    /** 手机号码 */
    private String phone;

    /** 用户头像URL */
    private String avatar;

    /** 身份证号 */
    private String idCard;

    /** 性别：0-未知，1-男，2-女 */
    private Integer gender;

    /** 组织ID，用于组织架构管理 */
    private Long orgId;

    /** 租户ID，用于多租户系统 */
    private Long tenantId;

    /** JSON格式的扩展字段，用于存储额外的用户信息 */
    private String extJson;

    /** 用户状态：1-正常，0-禁用 */
    private Integer status;

    /** 备注信息 */
    private String remark;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除标志：0-正常，1-已删除 */
    private Integer deleted;
}
