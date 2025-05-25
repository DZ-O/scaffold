package com.beikdz.scaffold.util;


import cn.hutool.crypto.digest.BCrypt;

public class PasswordUtil {
    // 使用更安全的加密算法
    public static String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    // 密码强度检查
    public static boolean isStrongPassword(String password) {
        // 至少8位，包含大小写字母、数字和特殊字符
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(regex);
    }
}
