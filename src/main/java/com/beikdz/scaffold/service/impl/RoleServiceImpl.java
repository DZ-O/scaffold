package com.beikdz.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.model.Role;
import com.beikdz.scaffold.model.RolePermission;
import com.beikdz.scaffold.repository.PermissionMapper;
import com.beikdz.scaffold.repository.RoleMapper;
import com.beikdz.scaffold.repository.RolePermissionMapper;
import com.beikdz.scaffold.service.RoleService;
import com.beikdz.scaffold.service.dto.RoleQueryDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    private final RolePermissionMapper rolePermissionMapper;
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;
    public RoleServiceImpl(RolePermissionMapper rolePermissionMapper, PermissionMapper permissionMapper, RoleMapper roleMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    @Transactional
    public void assignPermissions(Long roleId, List<Long> permissionIds, Long tenantId) {
        // 先删除原有
        rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, roleId)
                .eq(Objects.nonNull(tenantId),RolePermission::getTenantId, tenantId));
        // 批量插入
        for (Long pid : permissionIds) {
            RolePermission rp = new RolePermission();
            rp.setRoleId(roleId);
            rp.setPermissionId(pid);
            rp.setTenantId(tenantId);
            rolePermissionMapper.insert(rp);
        }
    }

    @Override
    public List<Permission> getRolePermissions(Long roleId, Long tenantId) {
        List<RolePermission> rps = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getRoleId, roleId)
                .eq(Objects.nonNull(tenantId),RolePermission::getTenantId, tenantId));
        if (rps.isEmpty()) return List.of();
        List<Long> permIds = rps.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        return permissionMapper.selectBatchIds(permIds);
    }

    @Override
    public void createRole(Role role) {

    }

    @Override
    public void updateRole(Role role) {

    }

    @Override
    public void deleteRole(Long roleId) {

    }

    @Override
    public List<Role> getAllRoles(Long tenantId) {
        return List.of();
    }

    @Override
    public Page<Role> getRolesByCondition(RoleQueryDTO condition, int page, int size) {
        return null;
    }
}
