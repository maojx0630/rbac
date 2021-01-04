package com.github.maojx0630.rbac.common.config.login.login;

import com.github.maojx0630.rbac.common.config.login.UserUtils;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.sys.model.SysAcl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 权限校验
 * <br/>
 * @author MaoJiaXing
 * @date 2020-03-30 18:01 
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		//获取登录用户，未登录直接用户未登录
		List<SysAcl> list = UserUtils.getUser().getAclList();
		//忽略权限校验
		if(handler instanceof HandlerMethod){
			HandlerMethod method= (HandlerMethod) handler;
			if(method.hasMethodAnnotation(IgnoreLogin.class)||method.getBeanType().isAnnotationPresent(IgnoreLogin.class)){
				return true;
			}
		}
		//权限校验
		String uri = request.getRequestURI();
		String method = request.getMethod().toLowerCase();
		AntPathMatcher ant = new AntPathMatcher();
		for (SysAcl acl : list) {
			if (ant.match(acl.getUrl(), uri) && acl.getMethod().equalsIgnoreCase(method)) {
				return true;
			}
		}
		throw StateEnum.permission_denied.create();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
	                            Exception ex) {
	}

}
