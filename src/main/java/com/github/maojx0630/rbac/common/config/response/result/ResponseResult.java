package com.github.maojx0630.rbac.common.config.response.result;


import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;

import java.util.Objects;

/**
 * 统一返回值对象
 * @author MaoJiaXing
 */
public class ResponseResult implements ResponseResultState {

	private Integer state;

	private String msg;

	private Object data;

	public ResponseResult() {
	}

	private ResponseResult(ResponseResultState bodyState) {
		this.state = bodyState.getState();
		this.msg = bodyState.getMsg();
	}

	private ResponseResult(ResponseResultState bodyState, Object data) {
		this.state = bodyState.getState();
		this.msg = bodyState.getMsg();
		this.data = data;
	}

	public static ResponseResult of(ResponseResultState bodyState){
		return new ResponseResult(bodyState);
	}

	public static ResponseResult of(ResponseResultState bodyState,Object data){
		return new ResponseResult(bodyState,data);
	}

	public ResponseResult appendMsg(String msg) {
		this.msg = this.msg + msg;
		return this;
	}

	@Override
	public Integer getState() {
		return state;
	}

	public ResponseResult setState(Integer state) {
		this.state = state;
		return this;
	}

	@Override
	public String getMsg() {
		return msg;
	}

	public ResponseResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	@Override
	public Object getData() {
		return data;
	}

	public ResponseResult setData(Object data) {
		this.data = data;
		return this;
	}

	/**
	 * 向后追加信息
	 * <br/>
	 * @param msg 信息
	 * @return com.zfei.rehabilitation.common.config.response.result.ResponseResult
	 * @author MaoJiaXing
	 * @date 2020-12-07 14:06
	 */
	public ResponseResult append(String msg) {
		this.msg = this.msg + msg;
		return this;
	}

	/**
	 * 向前插入信息
	 * <br/>
	 * @param msg 信息
	 * @return com.zfei.rehabilitation.common.config.response.result.ResponseResult
	 * @author MaoJiaXing
	 * @date 2020-12-07 14:07
	 */
	public ResponseResult insert(String msg) {
		this.msg = msg + this.msg;
		return this;
	}

	public boolean success() {
		return Objects.equals(StateEnum.success.getState(), this.state);
	}

	public boolean failure() {
		return !success();
	}

	public boolean hasValid(){
		return Objects.equals(StateEnum.valid_error.getState(), this.state);
	}
}
