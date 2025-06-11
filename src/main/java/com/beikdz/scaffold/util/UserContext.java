package com.beikdz.scaffold.util;

import cn.dev33.satoken.stp.StpUtil;
import com.beikdz.scaffold.model.User;
import com.beikdz.scaffold.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用户上下文工具类
 * 提供获取当前登录用户信息和租户ID的方法
 */
@Component
public class UserContext {

    private static UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        UserContext.userService = userService;
    }

    /**
     * 获取当前登录用户ID
     * @return 用户ID
     */
    public static Long getCurrentUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    /**
     * 获取当前登录用户
     * @return 用户对象
     */
    public static User getCurrentUser() {
        Long userId = getCurrentUserId();
        return userService.getById(userId);
    }

    /**
     * 获取当前登录用户的租户ID
     * @return 租户ID
     */
    public static Long getCurrentTenantId() {
        User user = getCurrentUser();
        return user != null ? user.getTenantId() : null;
    }
}
