package com.github.maojx0630.rbac.common.config.response.serialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 将字典值自动转为label
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-01 14:59 
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictLabel {

	String value();

}
