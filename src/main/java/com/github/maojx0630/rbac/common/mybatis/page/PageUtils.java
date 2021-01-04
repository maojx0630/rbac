package com.github.maojx0630.rbac.common.mybatis.page;


import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 从request参数中获取page对象
 * @see Page 用来快速构造次对象 方便分页
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-25 15:45 
 */
@SuppressWarnings("all")
public class PageUtils {



	public static <T> Page<T> get() {
		return get(null);
	}

	public static <T> Page<T> get(T t) {
		return get(null,t);
	}

	public static <T> Page<T> get(Long sizeParam) {
		return get(GlobalStatic.DEFAULT_CURRENT_NAME, GlobalStatic.DEFAULT_SIZE_NAME, sizeParam,null);
	}

	public static <T> Page<T> get(Long sizeParam,T t) {
		return get(GlobalStatic.DEFAULT_CURRENT_NAME, GlobalStatic.DEFAULT_SIZE_NAME, sizeParam,t);
	}

	public static <T> Page<T> get(String currentName, String sizeName,T t) {
		return get(currentName, sizeName, null,t);
	}

	public static <T> Page<T> get(String currentName, String sizeName, Long sizeParam,T t) {

		ServletRequestAttributes servletRequestAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		long current = getPageParam(request, currentName, GlobalStatic.DEFAULT_CURRENT);
		if (Objects.nonNull(sizeParam) && String.valueOf(sizeParam).matches
				(GlobalStatic.POSITIVE_INTEGER_REGEX)) {
			return new Page<T>(current, sizeParam,t);
		}
		long size = getPageParam(request, sizeName, GlobalStatic.DEFAULT_SIZE);
		return new Page<T>(current, size,t);
	}

	public static <T> Page<T> create(Long current, Long size) {
		return new Page<T>(current, size,null);
	}

	public static <T> Page<T> create(Long current, Long size,T t) {
		return new Page<T>(current, size,t);
	}

	public static <T> Page<T> create(Long current) {
		return new Page<T>(current, GlobalStatic.DEFAULT_SIZE,null);
	}

	public static <T> Page<T> create(Long current,T t) {
		return new Page<T>(current, GlobalStatic.DEFAULT_SIZE,t);
	}

	private static long getPageParam(HttpServletRequest request, String paramName, long defaultValue) {
		String value = request.getParameter(paramName);
		if (StrUtil.isNotBlank(value) && value.matches(GlobalStatic.POSITIVE_INTEGER_REGEX)) {
			return Long.valueOf(value);
		} else {
			return defaultValue;
		}
	}

}
