package com.github.maojx0630.rbac.common.config.global;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 全局静态配置
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 18:02 
 */
public abstract class GlobalStatic {

	public static final Charset UTF8 = StandardCharsets.UTF_8;

	public static final String UTF_8 = "UTF-8";

	public static final String PHONE_REGEX = "^1[\\d]{10}$";

	//密码的组成至少要包括大小写字母、数字及标点符号的其中两项,并且8-16位
	public static final String PASSWORD_REGEX = "^(?![A-Za-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{8,16}$";

	//用户名的组成至少要包括大小写字母、数字的其中两项,并且6-16位
	public static final String USERNAME_REGEX = "^(?![A-Za-z]+$)(?![\\W_]+$)\\S{6,16}$";

	//ipv4 正则校验
	public static final String IP_REGEX="^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." +
			"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
			"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

	//正整数校验
	public static final String POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";

	//树顶级id
	public static final Long TREE_ROOT = 0L;

	//ant校验时额外添加的前缀
	public static final String ANT_BASE_URI = "";

	//默认页码
	public static final long DEFAULT_CURRENT = 1;

	//默认页码参数名
	public static final String DEFAULT_CURRENT_NAME = "current";

	//默认页长
	public static final long DEFAULT_SIZE = 10;

	//默认页长参数名
	public static final String DEFAULT_SIZE_NAME = "size";

	//绑定参数异常信息最大长度
	public static final int BINDING_MSG_MAX_LENGTH = 30;

	//绑定参数异常信息最大长度超过后显示的信息
	public static final String BINDING_MSG_MAX_LENGTH_MSG = "参数不正确,请确认";

	//token在header中的name
	public static final String AUTHENTICATION_HEAD = "authentication";
	public static final String UNIQUE_HEAD = "unique";

	//删除标识符
	public static final String DELETE_VALUE = GlobalStaticComponent.logicDeleteValue;

	//未删除标识符
	public static final String NOT_DELETE_VALUE = GlobalStaticComponent.logicNotDeleteValue;


}
