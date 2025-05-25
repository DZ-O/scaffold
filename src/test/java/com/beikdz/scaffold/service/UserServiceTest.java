package com.beikdz.scaffold.service;

import com.beikdz.scaffold.model.User;
import com.beikdz.scaffold.repository.PermissionMapper;
import com.beikdz.scaffold.repository.RoleMapper;
import com.beikdz.scaffold.repository.RolePermissionMapper;
import com.beikdz.scaffold.repository.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Test
    @Transactional
    public void initSuperAdmin() {
//        // 1. 创建超级管理员角色
////        Role adminRole = new Role();
//        adminRole.setCode("ADMIN");
//        adminRole.setName("管理员");
//        adminRole.setStatus(1);
//        adminRole.setRemark("系统初始化");
//        adminRole.setCreateTime(LocalDateTime.now());
//        adminRole.setUpdateTime(LocalDateTime.now());
//        roleMapper.insert(adminRole);

        // 2. 创建基础权限


//        Permission viewPermission = new Permission();
//        viewPermission.setParentId(1L);
//        viewPermission.setCode("USER_VIEW");
//        viewPermission.setName("用户查看");
//        viewPermission.setType("api");
//        viewPermission.setStatus(1);
//        viewPermission.setRemark("系统初始化");
//        viewPermission.setCreateTime(LocalDateTime.now());
//        viewPermission.setUpdateTime(LocalDateTime.now());
//        permissionMapper.insert(viewPermission);
//
//        Permission editPermission = new Permission();
//        editPermission.setParentId(1L);
//        editPermission.setCode("USER_EDIT");
//        editPermission.setName("用户编辑");
//        editPermission.setType("api");
//        editPermission.setStatus(1);
//        editPermission.setRemark("系统初始化");
//        editPermission.setCreateTime(LocalDateTime.now());
//        editPermission.setUpdateTime(LocalDateTime.now());
//        permissionMapper.insert(editPermission);

//        // 3. 关联角色和权限
//        RolePermission rolePermission1 = new RolePermission();
//        rolePermission1.setRoleId(1L);
//        rolePermission1.setPermissionId(1L);
//        rolePermission1.setCreateTime(LocalDateTime.now());
//        rolePermissionMapper.insert(rolePermission1);
//
//        RolePermission rolePermission2 = new RolePermission();
//        rolePermission2.setRoleId(1L);
//        rolePermission2.setPermissionId(2L);
//        rolePermission2.setCreateTime(LocalDateTime.now());
//        rolePermissionMapper.insert(rolePermission2);
//
//        RolePermission rolePermission3 = new RolePermission();
//        rolePermission3.setRoleId(1L);
//        rolePermission3.setPermissionId(3L);
//        rolePermission3.setCreateTime(LocalDateTime.now());
//        rolePermissionMapper.insert(rolePermission3);

//        // 4. 创建超级管理员用户
//        userService.register("daizhen", "123456", "ADMIN");
//        User admin = userService.findByUsername("daizhen");
//
//        // 5. 分配角色给用户
//        userService.assignRoles(admin.getId(), List.of(1L), null);
//
//        System.out.println("管理员初始化完成！");
//        System.out.println("用户名：daizhen");
//        System.out.println("密码：123456");
//        System.out.println("角色：ADMIN");
//        System.out.println("权限：ALL, USER_VIEW, USER_EDIT");
    }

    @Test
    @Transactional
    public void testLogin() {
        // 1. 初始化管理员账号
//        initSuperAdmin();

        // 2. 测试正确密码登录
        User loginUser = userService.login("daizhen", "123456");
        assert loginUser != null;
        assert "daizhen".equals(loginUser.getUsername());
        assert loginUser.getStatus() == 1;

        // 3. 测试错误密码登录
        try {
            userService.login("daizhen", "wrong_password");
            assert false : "应该抛出用户名或密码错误异常";
        } catch (Exception e) {
            assert e.getMessage().contains("用户名或密码错误");
        }

        // 4. 测试不存在的用户登录
        try {
            userService.login("not_exist", "any_password");
            assert false : "应该抛出用户名或密码错误异常";
        } catch (Exception e) {
            assert e.getMessage().contains("用户名或密码错误");
        }
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        // 1. 初始化管理员账号
        initSuperAdmin();
        User admin = userService.findByUsername("admin");

        // 2. 更新基本信息
        admin.setNickname("新管理员");
        admin.setEmail("new_admin@example.com");
        admin.setPhone("13888888888");
        admin.setGender(1);
        userMapper.updateById(admin);

        // 3. 验证更新结果
        User updatedAdmin = userService.findByUsername("admin");
        assert "新管理员".equals(updatedAdmin.getNickname());
        assert "new_admin@example.com".equals(updatedAdmin.getEmail());
        assert "13888888888".equals(updatedAdmin.getPhone());
        assert updatedAdmin.getGender() == 1;

        // 4. 测试修改密码
        String newPassword = "$2a$10$newpasswordhash";
        admin.setPassword(newPassword);
        userMapper.updateById(admin);

        // 5. 验证新密码登录
        User loginUser = userService.login("admin", newPassword);
        assert loginUser != null;
        assert "admin".equals(loginUser.getUsername());
    }
}
