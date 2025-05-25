package com.beikdz.scaffold.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.service.dto.PermissionTreeNode;
import java.util.List;

public interface PermissionService extends IService<Permission> {
    List<PermissionTreeNode> getPermissionTree(Long tenantId);
    void createPermission(Permission permission);
    void updatePermission(Permission permission);
    void deletePermission(Long permissionId);
    List<Permission> getAllPermissions(Long tenantId);
    List<String> getPermissionCodesByRoleId(Long roleId);
}
