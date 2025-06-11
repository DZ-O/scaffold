package com.beikdz.scaffold.model.request;

import com.beikdz.scaffold.enums.DeviceType;
import lombok.Data;

/**
 * 登录请求对象
 */
@Data
public class LoginRequest {
    /** 用户名 */
    private String username;
    
    /** 密码 */
    private String password;
    
    /** 设备类型 */
    private DeviceType deviceType = DeviceType.OTHER;
}