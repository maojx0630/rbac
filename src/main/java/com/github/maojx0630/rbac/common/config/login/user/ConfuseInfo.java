package com.github.maojx0630.rbac.common.config.login.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登陆信息混淆
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-11 15:45 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfuseInfo {

	private Long id;

	private String uuid;

	private String hex;

}
