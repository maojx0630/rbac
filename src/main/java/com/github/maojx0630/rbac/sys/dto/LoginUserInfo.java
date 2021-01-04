package com.github.maojx0630.rbac.sys.dto;

import com.github.maojx0630.rbac.sys.model.SysAcl;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-21 14:39 
 */
@Data
@NoArgsConstructor
public class LoginUserInfo {

	/**
	 * 用户id
	 */
	private Long id;

	/**
	 * 缓存的用户权限
	 */
	private List<SysAcl> aclList;
}
