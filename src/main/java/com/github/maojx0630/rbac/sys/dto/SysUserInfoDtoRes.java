package com.github.maojx0630.rbac.sys.dto;

import com.github.maojx0630.rbac.common.config.response.serialize.DictLabel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-08 14:44 
 */
@Data
@ApiModel("用户个人信息")
public class SysUserInfoDtoRes {

	@ApiModelProperty("名字")
	private String name;

	@ApiModelProperty("手机号")
	private String phone;

	@DictLabel("test")
	@ApiModelProperty("当前时间")
	private Date date=new Date();
}
