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
import com.beikdz.scaffold.service.request.LoginRequest;
import com.beikdz.scaffold.service.request.RegisterRequest;
import com.beikdz.scaffold.service.response.LoginResp;
import com.beikdz.scaffold.util.RequestUtil;
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

        // 登录并设置设备类型
        StpUtil.login(user.getId(), RequestUtil.getDeviceType().getCode());

        // 构建响应
        LoginResp resp = new LoginResp();
        resp.setToken(StpUtil.getTokenValue());
        resp.setUserId(user.getId());
        resp.setUsername(user.getUsername());
        resp.setRoles(roleCodes);
        resp.setDeviceType(RequestUtil.getHeader("deviceType"));
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
        String loginDeviceIdByToken = StpUtil.getLoginDeviceIdByToken(StpUtil.getTokenValue());
        String loginDeviceType = StpUtil.getLoginDeviceType();
        String deviceId = StpUtil.getLoginDeviceId();
        Long userId = StpUtil.getLoginIdAsLong();
        User user = userService.getById(StpUtil.getLoginId().toString());
        return ApiResponse.success(user);
    }

    @GetMapping("/logout")
    @UserActionLogAnno(action = UserActionType.LOGOUT)
    public ApiResponse<String> logout() {
        StpUtil.logout(StpUtil.getLoginIdAsLong(),StpUtil.getLoginDeviceType());
        return ApiResponse.success("登出成功");
    }

}
