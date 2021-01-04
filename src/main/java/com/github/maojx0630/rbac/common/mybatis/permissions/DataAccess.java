package com.github.maojx0630.rbac.common.mybatis.permissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据权限控制，加在mapper方法上
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-07 09:05 
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataAccess {
}
