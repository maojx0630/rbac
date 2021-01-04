package com.github.maojx0630.rbac.common.config.login.user;

import lombok.Data;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-11 16:57 
 */
@Data
public class UserLoginDeviceInfo {

	private String uuid;

	private long LoginDate;

	private String device;
}
