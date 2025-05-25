package com.beikdz.scaffold.util;

public class SensitiveUtil {
    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 7) return phone;
        return phone.substring(0, 3) + "****" + phone.substring(phone.length() - 4);
    }
    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return email;
        int idx = email.indexOf("@");
        if (idx <= 1) return "*" + email.substring(idx);
        return email.charAt(0) + "****" + email.substring(idx - 1);
    }
    public static String maskIdCard(String idCard) {
        if (idCard == null || idCard.length() < 8) return idCard;
        return idCard.substring(0, 4) + "****" + idCard.substring(idCard.length() - 4);
    }
    public static String maskName(String name) {
        if (name == null || name.length() < 2) return name;
        return name.charAt(0) + "*".repeat(name.length() - 1);
    }
    public static String maskAddress(String address) {
        if (address == null || address.length() < 6) return address;
        return address.substring(0, 3) + "****";
    }
}
