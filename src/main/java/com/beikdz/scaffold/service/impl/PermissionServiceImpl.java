package com.beikdz.scaffold.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.repository.PermissionMapper;
import com.beikdz.scaffold.service.PermissionService;
import com.beikdz.scaffold.service.dto.PermissionTreeNode;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    private final PermissionMapper permissionMapper;
    public PermissionServiceImpl(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionTreeNode> getPermissionTree(Long tenantId) {
        List<Permission> all = permissionMapper.selectList(new LambdaQueryWrapper<Permission>()
                .eq(Objects.nonNull(tenantId),Permission::getTenantId, tenantId)
                .eq(Permission::getDeleted, 0));
        return buildTree(0L, all);
    }

    @Override
    public void createPermission(Permission permission) {

    }

    @Override
    public void updatePermission(Permission permission) {

    }

    @Override
    public void deletePermission(Long permissionId) {

    }

    @Override
    public List<Permission> getAllPermissions(Long tenantId) {
        return List.of();
    }

    @Override
    public List<String> getPermissionCodesByRoleId(Long roleId) {
        return List.of();
    }

    private List<PermissionTreeNode> buildTree(Long parentId, List<Permission> all) {
        List<PermissionTreeNode> list = new ArrayList<>();
        for (Permission p : all) {
            if ((p.getParentId() == null && parentId == 0L) || (p.getParentId() != null && p.getParentId().equals(parentId))) {
                PermissionTreeNode node = new PermissionTreeNode();
                node.setId(p.getId());
                node.setName(p.getName());
                node.setCode(p.getCode());
                node.setType(p.getType());
                node.setChildren(buildTree(p.getId(), all));
                list.add(node);
            }
        }
        return list;
    }
}
