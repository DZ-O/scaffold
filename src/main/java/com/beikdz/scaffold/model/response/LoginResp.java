package com.beikdz.scaffold.model.response;

import com.beikdz.scaffold.enums.DeviceType;
import lombok.Data;

import java.util.List;

/**
 * 登录响应对象
 */
@Data
public class LoginResp {
    /** 认证令牌 */
    private String token;
    
    /** 用户ID */
    private Long userId;
    
    /** 用户名 */
    private String username;
    
    /** 角色列表 */
    private List<String> roles;
    
    /** 设备类型 */
    private String deviceType;
}