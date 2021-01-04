package com.github.maojx0630.rbac.sys.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-25 09:24 
 */
@Data
@ApiModel("密码登陆请求对象")
public class PwLoginParam {

	@NotBlank
	@ApiModelProperty(value = "用户名", required = true)
	private String username;

	@NotBlank
	@ApiModelProperty(value = "密码", required = true)
	private String password;
}
