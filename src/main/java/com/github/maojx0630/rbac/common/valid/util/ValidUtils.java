package com.github.maojx0630.rbac.common.valid.util;

import com.github.maojx0630.rbac.common.config.response.advice.BindingResultMsg;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.config.response.result.ResponseResult;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 手动调用验证信息
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-18 08:44 
 */
public class ValidUtils {
	private static Validator VALIDATOR = Validation.byProvider(HibernateValidator.class).
			configure().failFast(false).buildValidatorFactory().getValidator();


	/**
	 * 手动调用对象valid
	 * <br/>
	 * @param t 校验对象
	 * @return com.zfei.rehabilitation.common.config.response.result.ResponseResult
	 * @author MaoJiaXing
	 * @date 2020-12-18 10:45
	 */
	public static <T> ResponseResult valid(T t) {
		Set<ConstraintViolation<T>> validateResult = VALIDATOR.validate(t);

		if(validateResult.size()>0) {
			List<BindingResultMsg> list = new LinkedList<>();
			validateResult.forEach(err -> list.add(new BindingResultMsg
					(err.getPropertyPath().toString(), err.getMessage(), err.getInvalidValue())));
			return ResponseResult.of(StateEnum.valid_error).
					setData(list).appendMsg(" >>> 错误数量 : " + validateResult.size());
		}else{
			return ResponseResult.of(StateEnum.success);
		}
	}

}
