package com.github.maojx0630.rbac.common.base;

import com.github.maojx0630.rbac.common.config.global.GlobalStatic;

import java.io.Serializable;

/**
 * 实现接口可以xml中获取删除标识符
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 09:58 
 */
public interface ModelDeleteInfo extends Serializable {

	default String getDeleteValue() {
		return GlobalStatic.DELETE_VALUE;
	}

	default String getNotDeleteValue() {
		return GlobalStatic.NOT_DELETE_VALUE;
	}
}
