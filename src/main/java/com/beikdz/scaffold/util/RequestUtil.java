package com.beikdz.scaffold.util;

import com.beikdz.scaffold.enums.DeviceType;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求工具类
 * 用于从请求头中获取各种参数
 */
public class RequestUtil {

    /**
     * 获取当前请求对象
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取请求头中的参数
     * @param headerName 请求头名称
     * @return 请求头的值
     */
    public static String getHeader(String headerName) {
        HttpServletRequest request = getRequest();
        return request != null ? request.getHeader(headerName) : null;
    }

    /**
     * 获取请求头中的设备类型
     * @param headerName 请求头名称，默认为 "Device-Type"
     * @return 设备类型枚举值，如果没有则返回 OTHER
     */
    public static DeviceType getDeviceType(String headerName) {
        String deviceType = getHeader(headerName != null ? headerName : "Device-Type");
        if (deviceType == null) {
            return DeviceType.OTHER;
        }
        try {
            return DeviceType.valueOf(deviceType.toUpperCase());
        } catch (IllegalArgumentException e) {
            return DeviceType.OTHER;
        }
    }

    /**
     * 获取请求头中的设备类型，使用默认的请求头名称 "Device-Type"
     * @return 设备类型枚举值，如果没有则返回 OTHER
     */
    public static DeviceType getDeviceType() {
        return getDeviceType("Device-Type");
    }

    /**
     * 获取请求的完整URL
     */
    public static String getRequestURL() {
        HttpServletRequest request = getRequest();
        return request != null ? request.getRequestURL().toString() : "";
    }

    /**
     * 获取请求的IP地址
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return "";
        }
        
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取用户代理字符串
     */
    public static String getUserAgent() {
        return getHeader("User-Agent");
    }
}