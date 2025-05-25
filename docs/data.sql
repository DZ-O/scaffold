-- 用户表初始化
INSERT INTO `user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `avatar`, `id_card`, `gender`, `org_id`, `tenant_id`, `ext_json`, `status`, `remark`, `create_time`, `update_time`, `deleted`) VALUES
(1, 'admin', '$2a$10$adminpwdhash', '超级管理员', 'admin@example.com', '13800000000', NULL, NULL, 1, NULL, NULL, NULL, 1, '系统初始化', NOW(), NOW(), 0),
(2, 'user', '$2a$10$userpwdhash', '普通用户', 'user@example.com', '13900000000', NULL, NULL, 1, NULL, NULL, NULL, 1, '系统初始化', NOW(), NOW(), 0);

-- 角色表初始化
INSERT INTO `role` (`id`, `code`, `name`, `tenant_id`, `status`, `remark`, `create_time`, `update_time`, `deleted`) VALUES
(1, 'ADMIN', '管理员', NULL, 1, '系统初始化', NOW(), NOW(), 0),
(2, 'USER', '用户', NULL, 1, '系统初始化', NOW(), NOW(), 0);

-- 权限表初始化
INSERT INTO `permission` (`id`, `parent_id`, `code`, `name`, `type`, `tenant_id`, `status`, `remark`, `create_time`, `update_time`, `deleted`) VALUES
(1, 0, 'ALL', '全部权限', 'menu', NULL, 1, '系统初始化', NOW(), NOW(), 0),
(2, 1, 'USER_VIEW', '用户查看', 'api', NULL, 1, '系统初始化', NOW(), NOW(), 0),
(3, 1, 'USER_EDIT', '用户编辑', 'api', NULL, 1, '系统初始化', NOW(), NOW(), 0);

-- 用户角色关联
INSERT INTO `user_role` (`id`, `user_id`, `role_id`, `tenant_id`, `create_time`) VALUES
(1, 1, 1, NULL, NOW()),
(2, 2, 2, NULL, NOW());

-- 角色权限关联
INSERT INTO `role_permission` (`id`, `role_id`, `permission_id`, `tenant_id`, `create_time`) VALUES
(1, 1, 1, NULL, NOW()),
(2, 1, 2, NULL, NOW()),
(3, 1, 3, NULL, NOW()),
(4, 2, 2, NULL, NOW());

-- 用户操作白名单
INSERT INTO `user_action_log_white` (`id`, `type`, `value`, `remark`, `create_time`) VALUES
(1, 'user', '1', 'admin白名单', NOW()); 