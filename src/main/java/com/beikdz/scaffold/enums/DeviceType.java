package com.beikdz.scaffold.enums;

/**
 * @author daizhen
 * @Description 设备类型枚举
 * @create 2025-05-25 17:29
 */
public enum DeviceType {
    ANDROID("Android", "安卓设备"),
    IOS("iOS", "苹果设备"),
    WINDOWS("Windows", "Windows设备"),
    MAC("Mac", "Mac设备"),
    LINUX("Linux", "Linux设备"),
    OTHER("Other", "其他设备"),


    PC("PC", "个人电脑"),
    MOBILE("Mobile", "移动设备"),
    WECHAT("WeChat", "微信设备"),
    ;

    private final String code;
    private final String description;

    DeviceType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}