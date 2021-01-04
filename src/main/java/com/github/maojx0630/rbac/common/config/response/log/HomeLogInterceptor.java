package com.github.maojx0630.rbac.common.config.response.log;

import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.config.response.exception.StateException;
import com.github.maojx0630.rbac.common.config.login.UserUtils;
import com.github.maojx0630.rbac.common.utils.ResponseUtils;
import com.github.maojx0630.rbac.common.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志拦截器
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 18:54
 */
public class HomeLogInterceptor implements HandlerInterceptor {

	private UserUtils userUtils;

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeLogInterceptor.class);

	private static final ThreadLocal<Long> LONG_THREAD_LOCAL = new ThreadLocal<>();

	public HomeLogInterceptor(UserUtils userUtils) {
		this.userUtils = userUtils;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
	                         Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			ResponseUtils.response(response, StateEnum.no_found.build().
					append("{" + request.getRequestURI() + "}"));
			return false;
		}
		try {
			userUtils.validation();
		} catch (StateException ignored) {
		}
		LONG_THREAD_LOCAL.set(System.currentTimeMillis());
		LOGGER.info("▁▂▃▄▅▆▇█本次请求路径 {} █▇▆▅▄▃▂▁ ", request.getRequestURI());
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
	                            Object handler, Exception ex) {
		long begin = getAndRemove();
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		LOGGER.info("▁▂▃▄▅▆▇█执行完成 {} 方法█▇▆▅▄▃▂▁ 耗时：{} ms", handlerMethod.getMethod().getName(),
				System.currentTimeMillis() - begin);
		endThreadLocalRemove();
	}


	private Long getAndRemove(){
		Long aLong = LONG_THREAD_LOCAL.get();
		LONG_THREAD_LOCAL.remove();
		return aLong;
	}

	//结束后调用ThreadLocalRemove
	private void endThreadLocalRemove(){
		SpringUtils.getBeanList(ThreadLocalRemove.class).forEach(ThreadLocalRemove::remove);
	}

}
