package com.github.maojx0630.rbac.common.utils;

import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import okhttp3.Call;
import okhttp3.Request;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-14 11:25 
 */
public abstract class RequestUtils {

	/**
	 * 获取当前请求的request
	 * <br/>
	 * @return javax.servlet.http.HttpServletRequest
	 * @author MaoJiaXing
	 * @date 2020-12-14 11:26
	 */
	public static HttpServletRequest getRequest() {
		ServletRequestAttributes servletRequestAttributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return servletRequestAttributes.getRequest();
	}

	/**
	 * 获取指定请求参数
	 * <br/>
	 * @param string name
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2020-04-13 14:55
	 */
	public static String getParam(String string) {
		return getRequest().getParameter(string);
	}

	/**
	 * 获取指定Header
	 * <br/>
	 * @param string name
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2020-04-13 14:55
	 */
	public static String getHeader(String string) {
		return getRequest().getHeader(string);
	}

	/**
	 * 获取具体ip
	 * <br/>
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2020-12-14 14:12
	 */
	public static String getIp() {
		HttpServletRequest request = getRequest();
		String Xip = request.getHeader("X-Real-IP");
		String XFor = request.getHeader("X-Forwarded-For");
		if (StrUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = XFor.indexOf(",");
			if (index != -1) {
				return XFor.substring(0, index);
			} else {
				return XFor;
			}
		}
		XFor = Xip;
		if (StrUtil.isNotEmpty(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
			return XFor;
		}
		if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("Proxy-Client-IP");
		}
		if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StrUtil.isBlank(XFor) || "unknown".equalsIgnoreCase(XFor)) {
			XFor = request.getRemoteAddr();
		}
		return XFor;
	}

	/**
	 * 通过ip获取具体地址
	 * http://whois.pconline.com.cn/
	 * 太平洋网络ip查询
	 * <br/>
	 * @param ip ip
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2020-12-14 14:12
	 */

	public static String getIpAddress(String ip) {
		if (StrUtil.isBlank(ip)) {
			return null;
		}
		if(!ip.matches(GlobalStatic.IP_REGEX)) {
			return "ip is not legal : " + ip;
		}
		Call call = OkHttpUtils.getCall(new Request.Builder().get().
				url("http://whois.pconline.com.cn/ip.jsp?ip=" + ip.trim()).build());
		try {
			String str=call.execute().body().string();
			str=str.replaceAll("\n","");
			str=str.replaceAll("\r","");
			return str;
		} catch (Exception e) {
			e.printStackTrace();
			return "get addr error "+e.getMessage();
		}
	}


}
