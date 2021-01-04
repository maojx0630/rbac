package com.github.maojx0630.rbac.common.valid;

import com.github.maojx0630.rbac.common.valid.validator.StatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 状态值校验
 * <br/>
 * @author MaoJiaXing
 * @date 2020-03-23 16:13 
 */
@NotBlank(message = Status.msg)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {StatusValidator.class})
public @interface Status {

	String[] value();

	String msg = "状态值不正确";

	String message() default msg;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
