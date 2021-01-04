package com.github.maojx0630.rbac.common.config.login.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-14 11:32 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginInfo {

	private Long id;

	private String ip;

	private String loginAddr;

	private String device;

	private Long expire;

	private boolean fixedDate=false;

}
