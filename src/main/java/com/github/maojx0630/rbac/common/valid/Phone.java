package com.github.maojx0630.rbac.common.valid;

import com.github.maojx0630.rbac.common.config.global.GlobalStatic;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 手机号码校验注解
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 21:34 
 */
@Pattern(regexp = GlobalStatic.PHONE_REGEX, message = Phone.msg)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Phone {

	String msg = "手机号码格式不正确";

	String message() default msg;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
