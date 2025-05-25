package com.beikdz.scaffold.enums;

/**
 * 用户行为类型枚举
 * 定义系统中所有可追踪的用户操作类型
 */
public enum UserActionType {
    /** 用户登录操作 */
    LOGIN("登录"),

    /** 用户登出操作 */
    LOGOUT("登出"),

    /** 用户注册操作 */
    REGISTER("注册"),

    /** 用户修改个人资料操作 */
    UPDATE_PROFILE("修改资料"),

    /** 删除用户操作 */
    DELETE_USER("删除用户");

    /** 行为类型的中文描述 */
    private final String desc;

    /**
     * 构造函数
     * @param desc 行为类型的描述文本
     */
    UserActionType(String desc) { this.desc = desc; }

    /**
     * 获取行为类型的描述
     * @return 行为类型的描述文本
     */
    public String getDesc() { return desc; }
}
