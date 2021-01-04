package com.github.maojx0630.rbac.common.config.login.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-21 15:26 
 */
@Data
@ApiModel("登陆返回信息")
@NoArgsConstructor
@AllArgsConstructor
public class PublicAndUnique {

	@ApiModelProperty(value = "公钥",required = true)
	private String publicKey;

	@ApiModelProperty(value = "用户标识",required = true)
	private String unique;
}
