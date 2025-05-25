package com.beikdz.scaffold.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beikdz.scaffold.common.ApiResponse;
import com.beikdz.scaffold.model.User;
import com.beikdz.scaffold.repository.UserMapper;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserMapper userMapper;

    @SaCheckRole("admin")
    @GetMapping("/list")
    public ApiResponse<IPage<User>> list(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        IPage<User> userPage = userMapper.selectPage(new Page<>(page, size), null);
        return ApiResponse.success(userPage);
    }

    @SaCheckRole("admin")
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody User user) {
        userMapper.insert(user);
        return ApiResponse.success("添加成功");
    }

    @SaCheckRole("admin")
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody User user) {
        userMapper.updateById(user);
        return ApiResponse.success("更新成功");
    }

    @SaCheckRole("admin")
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        userMapper.deleteById(id);
        return ApiResponse.success("删除成功");
    }
}
