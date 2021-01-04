package com.github.maojx0630.rbac.common.config.response.exception;


import com.github.maojx0630.rbac.common.config.response.result.ResponseResult;
import com.github.maojx0630.rbac.common.config.response.result.ResponseResultState;
import io.swagger.annotations.ApiModel;

import java.util.function.Supplier;

/**
 * 错误码
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 18:55
 */
@ApiModel("状态值")
public enum StateEnum implements ResponseResultState, Supplier<StateException> {

	/**
	 * 成功
	 */
	success(200, "OK"),
	/**
	 * 无数据返回
	 */
	no_data(300, "no data"),
	/**
	 * 访问的接口不存在
	 */
	no_found(404, "访问的接口不存在 "),
	/**
	 * 发生了意料之外的异常
	 */
	error(500, "发生了未知异常，请您稍后再试！"),
	//550系统相关问题
	file_tag_error(550,"文件不能正常被hash"),
	file_empty(551,"文件不能为空"),
	file_has_no_suffix(552,"文件没有后缀名"),
	file_cannot_save(553,"文件不能被保存"),
	file_does_not_exist(554,"文件不存在"),
	file_download_error(555,"下载文件异常"),
	file_max_upload_size_exceeded(556,"文件超出规定大小"),
	//600登陆相关
	/**
	 * 用户未登录
	 */
	user_not_login(600, "用户未登录"),
	/**
	 * token校验失败
	 */
	token_check_failure(601, "token校验失败"),
	/**
	 * token禁止重复使用
	 */
	token_repeat(602, "token不能重复使用"),
	/**
	 * token超时
	 */
	token_request_timeout(603, "token单次请求超时，请重试"),
	/**
	 * 没有权限
	 */
	permission_denied(650, "没有权限"),

	//700参数及类型相关
	/**
	 * 必须拥有默认构造方法
	 */
	default_structure(701, "必须拥有默认构造方法"),
	/**
	 * 参数校验异常
	 */
	valid_error(702, "参数校验失败"),
	/**
	 * 参数已存在
	 */
	param_exist(703, "已存在"),
	/**
	 * 参数不存在
	 */
	param_nonentity(704, "不存在"),
	/**
	 * 无对应数据
	 */
	no_relevant_data(705,"无相关数据"),
	//800增删改相关状态
	/**
	 * 修改失败
	 */
	update_error(801, "修改失败"),
	/**
	 * 添加失败
	 */
	add_error(802, "添加失败"),
	/**
	 * 删除失败
	 */
	delete_error(803, "删除失败");
	//工具类等调用错误 导致出现错误  1000

	private Integer state;

	private String msg;

	StateEnum(Integer state, String msg) {
		this.state = state;
		this.msg = msg;
	}

	@Override
	public Integer getState() {
		return state;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	public StateException create() {
		return new StateException(this);
	}

	@Override
	public StateException get() {
		return create();
	}

	public ResponseResult build() {
		return ResponseResult.of(this);
	}
}
