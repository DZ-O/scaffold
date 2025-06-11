package com.beikdz.scaffold.model.request;

import lombok.Data;

/**
 * 注册请求对象
 */
@Data
public class RegisterRequest {
    /** 用户名 */
    private String username;
    
    /** 密码 */
    private String password;
    
    /** 角色 */
    private String role;
}