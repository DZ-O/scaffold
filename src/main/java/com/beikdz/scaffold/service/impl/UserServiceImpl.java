package com.beikdz.scaffold.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beikdz.scaffold.model.*;
import com.beikdz.scaffold.repository.*;
import com.beikdz.scaffold.service.UserService;
import com.beikdz.scaffold.service.dto.UserQueryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private final UserMapper userMapper;
    @Autowired
    private final UserRoleMapper userRoleMapper;
    @Autowired
    private final RoleMapper roleMapper;
    @Autowired
    private final RolePermissionMapper rolePermissionMapper;
    @Autowired
    private final PermissionMapper permissionMapper;
    public UserServiceImpl(UserMapper userMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper, RolePermissionMapper rolePermissionMapper, PermissionMapper permissionMapper) {
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public void register(String username, String password, String role) {
        Assert.hasText(username, "用户名不能为空");
        Assert.hasText(password, "密码不能为空");
        if (findByUsername(username) != null) throw new RuntimeException("用户名已存在");
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(password));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        User user = findByUsername(username);
        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }
        return user;
    }

    @Override
    public void assignRoles(Long userId, List<Long> roleIds, Long tenantId) {
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(Objects.nonNull(tenantId),UserRole::getTenantId, tenantId));
        for (Long rid : roleIds) {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(rid);
            ur.setTenantId(tenantId);
            userRoleMapper.insert(ur);
        }
    }

    @Override
    public List<Role> getUserRoles(Long userId, Long tenantId) {
        List<UserRole> urs = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userId)
                .eq(Objects.nonNull(tenantId),UserRole::getTenantId, tenantId));
        if (urs.isEmpty()) return List.of();
        List<Long> roleIds = new ArrayList<>();
        for (UserRole ur : urs) roleIds.add(ur.getRoleId());
        return roleMapper.selectBatchIds(roleIds);
    }

    @Override
    public List<Permission> getUserPermissions(Long userId, Long tenantId) {
        List<Role> roles = getUserRoles(userId, tenantId);
        Set<Long> permIds = new HashSet<>();
        for (Role role : roles) {
            List<RolePermission> rps = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>()
                    .eq(RolePermission::getRoleId, role.getId())
                    .eq(Objects.nonNull(role.getTenantId()),RolePermission::getTenantId, tenantId));
            for (RolePermission rp : rps) permIds.add(rp.getPermissionId());
        }
        if (permIds.isEmpty()) return List.of();
        return permissionMapper.selectBatchIds(new ArrayList<>(permIds));
    }

    @Override
    public void resetPassword(String username, String newPassword) {

    }

    @Override
    public void lockUser(Long userId) {

    }

    @Override
    public void unlockUser(Long userId) {

    }

    @Override
    public void updateUserStatus(Long userId, Integer status) {

    }

    @Override
    public Page<User> findUsersByCondition(UserQueryDTO condition, int page, int size) {
        return null;
    }

    @Override
    public boolean checkUserPermission(Long userId, String permissionCode) {
        return false;
    }
}
