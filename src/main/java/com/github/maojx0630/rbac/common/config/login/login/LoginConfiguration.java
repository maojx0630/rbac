package com.github.maojx0630.rbac.common.config.login.login;

import com.github.maojx0630.rbac.common.config.global.MyProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 登陆拦截器
 * <br/>
 * @author MaoJiaXing
 * @date 2020-03-30 18:03
 */
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

	@Autowired
	private MyProperty myProperty;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns(myProperty.getIgnoreUrl())
				.order(Ordered.HIGHEST_PRECEDENCE + 10);
		if (myProperty.isSwagger()) {
			registration.excludePathPatterns(myProperty.getSwaggerUrl());
		}
	}
}
