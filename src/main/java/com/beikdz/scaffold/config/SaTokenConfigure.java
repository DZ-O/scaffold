package com.beikdz.scaffold.config;

import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    // 注册拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor()).addPathPatterns("/**").excludePathPatterns("/auth/login", "/auth/register", "/swagger-ui/**", "/v3/api-docs/**");
        // 注册 Sa-Token 拦截器
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 登录校验
            StpUtil.checkLogin();

            // 注入角色和权限信息
            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) StpUtil.getSession().get("roles");
            @SuppressWarnings("unchecked")
            List<String> permissions = (List<String>) StpUtil.getSession().get("permissions");
            if (roles != null) {
                StpUtil.getSession().set("roleList", roles);
            }
            if (permissions != null) {
                StpUtil.getSession().set("permissionList", permissions);
            }

            // 路由拦截
            SaRouter.match("/**").check(r -> {
                // 根据注解校验角色权限
                if (roles != null) {
                    for (String role : roles) {
                        if (role.equals("ADMIN")) {
                            return;
                        }
                    }
                }
            });
        }))
        .addPathPatterns("/**")
        .excludePathPatterns("/auth/login" // 登录接口
                , "/auth/register" // 注册接口
                , "/public/**" // 公共资源
                , "/error" // 错误处理
                , "/swagger-ui.html", "/webjars/**", "/v2/api-docs", "/swagger-resources/**" // Swagger 相关路径
        );
    }
}
