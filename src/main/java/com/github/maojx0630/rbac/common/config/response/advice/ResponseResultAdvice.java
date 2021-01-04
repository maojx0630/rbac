package com.github.maojx0630.rbac.common.config.response.advice;

import com.alibaba.fastjson.JSON;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.config.response.exception.StateException;
import com.github.maojx0630.rbac.common.config.response.result.ResponseResult;
import com.github.maojx0630.rbac.common.config.response.result.ResponseResultState;
import com.github.maojx0630.rbac.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * 控制增强,自定义异常处理
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 18:56
 */
@RestControllerAdvice
@Slf4j
public class ResponseResultAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		if (!Objects.requireNonNull(methodParameter.getMethod()).getDeclaringClass().getPackage().getName().
				startsWith(SpringUtils.getBasePackage())) {
			return false;
		}
		return !(methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseResultAdvice.class) || Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResponseResultAdvice.class));
	}
	//处理结果
	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<?
			extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
	                              ServerHttpResponse serverHttpResponse) {
		ResponseResultState responseResult;
		if (o instanceof ResponseResultState) {
			responseResult = (ResponseResultState) o;
		}else {
			responseResult = ResponseResult.of(StateEnum.success, o);
		}
		log.debug(JSON.toJSONString(responseResult));
		return responseResult;
	}



	/**
	 * 自定义异常处理
	 */
	@ExceptionHandler(StateException.class)
	public ResponseResultState stateException(StateException exception) {
		return ResponseResult.of(exception);
	}

	private ResponseResultState test(Throwable throwable, int number) {
		if (number > 3 || throwable == null) {
			return null;
		} else if (throwable instanceof StateException) {
			return ResponseResult.of((StateException) throwable);
		} else {
			number++;
			return test(throwable.getCause(), number);
		}
	}
	//自定义异常处理结束

	//参数校验异常区域
	@ExceptionHandler(BindException.class)
	public ResponseResultState constraintViolationException(BindException e) {
		return bindingResult(e.getBindingResult());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseResultState methodArgumentNotValidException(MethodArgumentNotValidException e) {
		return bindingResult(e.getBindingResult());
	}

	private ResponseResultState bindingResult(BindingResult e) {
		List<BindingResultMsg> list = new LinkedList<>();
		e.getFieldErrors().forEach(err -> list.
				add(new BindingResultMsg(err.getField(), err.getDefaultMessage(), err.getRejectedValue())));
		return ResponseResult.of(StateEnum.valid_error).
				setData(list).appendMsg(" >>> 错误数量 : " + e.getFieldErrorCount());
	}
	//参数校验区域结束

	/**
	 * 请求类型不匹配异常处理
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseResultState httpRequestMethodNotSupportedException() {
		return StateEnum.no_found.build();
	}

	//文件超出上限 如需增大修改spring yml
	// max-file-size:
	// max-request-size:
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseResultState maxUploadSizeExceededException(){
		return StateEnum.file_max_upload_size_exceeded.build();
	}

	/**
	 * 其他异常处理
	 */
	@ExceptionHandler(Exception.class)
	public ResponseResultState exception(Exception e) {
		ResponseResultState responseResult = test(e, 0);
		if (responseResult == null) {
			log.error("发生了异常，异常堆栈如下", e);
			return ResponseResult.of(StateEnum.error, e.getMessage());
		} else {
			return responseResult;
		}
	}


}
