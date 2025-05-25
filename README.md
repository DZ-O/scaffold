# Spring Boot 通用项目脚手架

## 功能模块
- MyBatis-Plus 持久层
- Redis 缓存
- Sa-Token 权限认证（支持JWT）
- 全局异常处理
- 统一响应返回结构
- 常用工具类（日期、字符串等）
- OpenAPI 3.0 接口文档（支持Apifox导入）

## 技术栈
- Spring Boot 3.x
- MyBatis-Plus
- Redis
- Sa-Token（含JWT模式）
- Lombok
- Hutool
- Springdoc OpenAPI

## 启动方式
1. 配置 `application.yml` 数据库和Redis信息
2. 执行 `mvn clean install`
3. 运行 `ScaffoldApplication.java`

## 接口文档
- 访问地址：http://localhost:8080/swagger-ui.html
- Apifox导入：选择OpenAPI 3.0，导入 http://localhost:8080/v3/api-docs

## 默认登录
- 用户名：admin
- 密码：123456

## Sa-Token 用法说明
- 登录接口：/login，返回token
- 需登录接口：在Controller方法加@SaCheckLogin注解，或全局拦截
- 判断是否登录：/isLogin
- token传递方式：请求头 Authorization: xxxxx

## 目录结构
```
src/main/java/com/example/scaffold/
  common/      # 通用响应、枚举
  config/      # 配置类
  controller/  # 控制器
  exception/   # 全局异常
  model/       # 实体类
  repository/  # 数据库访问
  security/    # 安全认证
  service/     # 业务逻辑
  util/        # 工具类
``` 