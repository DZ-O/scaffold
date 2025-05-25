package com.beikdz.scaffold.config;

import cn.dev33.satoken.stp.StpInterface;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.model.Role;
import com.beikdz.scaffold.service.UserService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StpInterfaceImpl implements StpInterface {
    @Resource
    private UserService userService;

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<Role> roles = userService.getUserRoles(Long.parseLong(loginId.toString()), null);
        return roles.stream().map(Role::getCode).collect(Collectors.toList());
    }

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<Permission> permissions = userService.getUserPermissions(Long.parseLong(loginId.toString()), null);
        return permissions.stream().map(Permission::getCode).collect(Collectors.toList());
    }
}
