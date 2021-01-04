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
 * 正整数校验
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-25 15:49 
 */
@Pattern(regexp = GlobalStatic.POSITIVE_INTEGER_REGEX, message = PositiveInteger.msg)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
public @interface PositiveInteger {

	String msg = "必须为正整数";

	String message() default msg;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
