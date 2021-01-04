package com.github.maojx0630.rbac.common.config.response.advice;

import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 异常信息绑定实体
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-22 21:24 
 */
@Data
public class BindingResultMsg implements Serializable {

	private String name;

	private String msg;

	private String incomingValue;

	public BindingResultMsg() {
	}

	public BindingResultMsg(String name, String msg, Object incomingValue) {
		this.name = name;
		if (StrUtil.isNotBlank(msg)) {
			if (msg.length() > GlobalStatic.BINDING_MSG_MAX_LENGTH) {
				this.msg = GlobalStatic.BINDING_MSG_MAX_LENGTH_MSG;
			} else {
				this.msg = msg;
			}
		} else {
			this.msg = GlobalStatic.BINDING_MSG_MAX_LENGTH_MSG;
		}
		if (Objects.nonNull(incomingValue)) {
			this.incomingValue = incomingValue.toString();
		} else {
			this.incomingValue = "null";
		}
	}
}
