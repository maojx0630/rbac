package com.github.maojx0630.rbac.common.valid.validator;

import cn.hutool.core.util.ObjectUtil;
import com.github.maojx0630.rbac.common.valid.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 状态值校验器
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 21:49 
 */
public class StatusValidator implements ConstraintValidator<Status, String> {

	private Status status;

	@Override
	public void initialize(Status constraintAnnotation) {
		this.status=constraintAnnotation;
	}

	@Override
	public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
		if (ObjectUtil.isNull(o)) {
			return true;
		}
		for (String s : status.value()) {
			if (s.equals(o)) {
				return true;
			}
		}
		return false;
	}
}
