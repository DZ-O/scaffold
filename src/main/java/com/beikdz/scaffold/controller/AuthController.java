package com.beikdz.scaffold.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.beikdz.scaffold.annotation.UserActionLogAnno;
import com.beikdz.scaffold.common.ApiResponse;
import com.beikdz.scaffold.enums.UserActionType;
import com.beikdz.scaffold.model.User;
import com.beikdz.scaffold.model.Role;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.service.UserService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    @UserActionLogAnno(action = UserActionType.LOGIN)
    public ApiResponse<LoginResp> login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getUsername(), request.getPassword());

        // 获取用户的角色和权限
        List<Role> roles = userService.getUserRoles(user.getId(), user.getTenantId());
        List<Permission> permissions = userService.getUserPermissions(user.getId(), user.getTenantId());

        // 提取角色和权限编码
        List<String> roleCodes = roles.stream().map(Role::getCode).collect(Collectors.toList());
        List<String> permissionCodes = permissions.stream().map(Permission::getCode).collect(Collectors.toList());

        // 登录
        StpUtil.login(user.getId());

        // 构建响应
        LoginResp resp = new LoginResp();
        resp.setToken(StpUtil.getTokenValue());
        resp.setUserId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setRoles(roleCodes);
        return ApiResponse.success(resp);
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest request) {
        userService.register(request.getUsername(), request.getPassword(), request.getRole());
        return ApiResponse.success("注册成功");
    }


    @SaCheckRole("ADMIN")
    @SaCheckPermission("USER:VIEW")
    @GetMapping("/profile")
    public ApiResponse<User> profile() {
        List<String> roleList = StpUtil.getRoleList();
        List<String> permissionList = StpUtil.getPermissionList();
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.getById(StpUtil.getLoginId().toString());
        return ApiResponse.success(user);
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }
    @Data
    public static class RegisterRequest {
        private String username;
        private String password;
        private String role;
    }
    @Data
    public static class LoginResp {
        private String token;
        private Long userId;
        private String username;
        private List<String> roles;
    }
}
