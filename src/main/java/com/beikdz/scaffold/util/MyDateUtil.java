package com.beikdz.scaffold.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import java.util.Date;

public class MyDateUtil {
    public static String format(Date date) {
        return DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
    }

    public static Date parse(String dateStr) {
        return DateUtil.parse(dateStr);
    }
}
