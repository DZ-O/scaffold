package com.beikdz.scaffold.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beikdz.scaffold.model.User;
import com.beikdz.scaffold.model.Permission;
import com.beikdz.scaffold.model.Role;
import com.beikdz.scaffold.service.dto.UserQueryDTO;

import java.util.List;

public interface UserService extends IService<User> {
    User findByUsername(String username);
    void register(String username, String password, String role);
    User login(String username, String password);
    void assignRoles(Long userId, List<Long> roleIds, Long tenantId);
    List<Role> getUserRoles(Long userId, Long tenantId);
    List<Permission> getUserPermissions(Long userId, Long tenantId);
    void resetPassword(String username, String newPassword);
    void lockUser(Long userId);
    void unlockUser(Long userId);
    void updateUserStatus(Long userId, Integer status);
    Page<User> findUsersByCondition(UserQueryDTO condition, int page, int size);
    boolean checkUserPermission(Long userId, String permissionCode);
}
