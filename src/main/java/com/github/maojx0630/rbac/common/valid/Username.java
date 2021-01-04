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
 * 用户名校验
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 21:34 
 */
@Pattern(regexp = GlobalStatic.USERNAME_REGEX, message = Username.msg)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Username {

	String msg = "用户名至少要包括大小写字母、数字其中两项,并6-16位";

	String message() default msg;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
