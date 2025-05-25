package com.beikdz.scaffold.controller;

import com.beikdz.scaffold.common.ApiResponse;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.model.Role;
import com.beikdz.scaffold.service.PermissionService;
import com.beikdz.scaffold.service.RoleService;
import com.beikdz.scaffold.service.UserService;
import com.beikdz.scaffold.service.dto.PermissionTreeNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rbac")
@Tag(name = "RBAC管理")
public class RbacController {
    @Resource private UserService userService;
    @Resource private RoleService roleService;
    @Resource private PermissionService permissionService;

    @Operation(summary = "给用户分配角色")
    @PostMapping("/user/assign-roles")
    public ApiResponse<Void> assignRoles(@RequestBody AssignRoleRequest req, @RequestHeader("tenant-id") Long tenantId) {
        userService.assignRoles(req.getUserId(), req.getRoleIds(), tenantId);
        return ApiResponse.success(null);
    }

    @Operation(summary = "给角色分配权限")
    @PostMapping("/role/assign-permissions")
    public ApiResponse<Void> assignPermissions(@RequestBody AssignPermissionRequest req, @RequestHeader("tenant-id") Long tenantId) {
        roleService.assignPermissions(req.getRoleId(), req.getPermissionIds(), tenantId);
        return ApiResponse.success(null);
    }

    @Operation(summary = "查询用户所有角色")
    @GetMapping("/user/{userId}/roles")
    public ApiResponse<List<Role>> getUserRoles(@PathVariable Long userId, @RequestHeader("tenant-id") Long tenantId) {
        return ApiResponse.success(userService.getUserRoles(userId, tenantId));
    }

    @Operation(summary = "查询用户所有权限")
    @GetMapping("/user/{userId}/permissions")
    public ApiResponse<List<Permission>> getUserPermissions(@PathVariable Long userId, @RequestHeader("tenant-id") Long tenantId) {
        return ApiResponse.success(userService.getUserPermissions(userId, tenantId));
    }

    @Operation(summary = "查询角色所有权限")
    @GetMapping("/role/{roleId}/permissions")
    public ApiResponse<List<Permission>> getRolePermissions(@PathVariable Long roleId, @RequestHeader("tenant-id") Long tenantId) {
        return ApiResponse.success(roleService.getRolePermissions(roleId, tenantId));
    }

    @Operation(summary = "查询权限树")
    @GetMapping("/permission/tree")
    public ApiResponse<List<PermissionTreeNode>> getPermissionTree(@RequestHeader("tenant-id") Long tenantId) {
        return ApiResponse.success(permissionService.getPermissionTree(tenantId));
    }

    // DTOs
    public static class AssignRoleRequest {
        private Long userId;
        private List<Long> roleIds;
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public List<Long> getRoleIds() { return roleIds; }
        public void setRoleIds(List<Long> roleIds) { this.roleIds = roleIds; }
    }
    public static class AssignPermissionRequest {
        private Long roleId;
        private List<Long> permissionIds;
        public Long getRoleId() { return roleId; }
        public void setRoleId(Long roleId) { this.roleId = roleId; }
        public List<Long> getPermissionIds() { return permissionIds; }
        public void setPermissionIds(List<Long> permissionIds) { this.permissionIds = permissionIds; }
    }
}
