package com.beikdz.scaffold.annotation;

import com.beikdz.scaffold.enums.UserActionType;
import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserActionLogAnno {
    UserActionType action();
    String module() default "";
    String content() default ""; // 支持SpEL表达式
    boolean ignore() default false; // 白名单
}
