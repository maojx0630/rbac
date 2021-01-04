package com.github.maojx0630.rbac.common.config.login.login;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加在controller方法上可以直接忽略权限校验
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-24 18:57 
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreLogin {
}
