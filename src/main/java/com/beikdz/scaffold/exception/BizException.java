package com.beikdz.scaffold.exception;

public class BizException extends RuntimeException {
    private final int code;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() { return code; }

    // 常用异常静态方法
    public static BizException notFound(String msg) {
        return new BizException(404, msg);
    }

    public static BizException forbidden(String msg) {
        return new BizException(403, msg);
    }

    public static BizException badRequest(String msg) {
        return new BizException(400, msg);
    }

    public static BizException serverError(String msg) {
        return new BizException(500, msg);
    }
}
