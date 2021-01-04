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
 * 密码校验注解
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 21:34 
 */
@Pattern(regexp = GlobalStatic.PASSWORD_REGEX, message = Password.msg)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface Password {

	String msg = "密码须8-16位,要包括大小写字母,数字及标点符号的其中两项";

	String message() default msg;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
