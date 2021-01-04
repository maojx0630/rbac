package com.github.maojx0630.rbac.sys.service;

import com.github.maojx0630.rbac.common.config.login.UserUtils;
import com.github.maojx0630.rbac.common.config.login.user.PublicAndUnique;
import com.github.maojx0630.rbac.sys.param.PwLoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-22 09:47 
 */
@Service
public class LoginService {

	@Autowired
	private UserUtils userUtils;

	public PublicAndUnique pwLogin(PwLoginParam loginParam) {
		return userUtils.login(1L,"设备",30*24*60*1000);
	}
}
