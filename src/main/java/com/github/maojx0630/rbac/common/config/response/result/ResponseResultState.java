package com.github.maojx0630.rbac.common.config.response.result;

/**
 * @author MaoJiaXing
 */
public interface ResponseResultState {

	/**
	 * 获取状态值
	 * <br/>
	 * @return java.lang.String
	 * @author MaoJiaXing
	 */
	Integer getState();

	/**
	 * 获取返回信息
	 * <br/>
	 * @return java.lang.String
	 * @author MaoJiaXing
	 */
	String getMsg();

	default Object getData() {
		return null;
	}

}
