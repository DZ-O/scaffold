package com.beikdz.scaffold.common;

public enum ResultCode {
    SUCCESS(200, "成功"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "未找到"),
    ERROR(500, "服务器错误");

    public final int code;
    public final String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
