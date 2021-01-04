package com.github.maojx0630.rbac.sys.controller;

import com.github.maojx0630.rbac.sys.service.LoginService;
import com.github.maojx0630.rbac.common.config.login.user.PublicAndUnique;
import com.github.maojx0630.rbac.sys.param.PwLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-31 18:14 
 */
@Api(tags = "用户登陆相关接口")
@RestController
@RequestMapping("login")
public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("pwLogin")
	@ApiOperation("用户密码登陆")
	public PublicAndUnique pwLogin(@RequestBody @Valid PwLoginParam loginParam) {
		return loginService.pwLogin(loginParam);
	}

}