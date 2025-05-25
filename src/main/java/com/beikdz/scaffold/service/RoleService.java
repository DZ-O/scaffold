package com.beikdz.scaffold.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.model.Role;
import com.beikdz.scaffold.service.dto.RoleQueryDTO;

import java.util.List;

public interface RoleService extends IService<Role> {
    void assignPermissions(Long roleId, List<Long> permissionIds, Long tenantId);
    List<Permission> getRolePermissions(Long roleId, Long tenantId);
    void createRole(Role role);
    void updateRole(Role role);
    void deleteRole(Long roleId);
    List<Role> getAllRoles(Long tenantId);
    Page<Role> getRolesByCondition(RoleQueryDTO condition, int page, int size);
}
