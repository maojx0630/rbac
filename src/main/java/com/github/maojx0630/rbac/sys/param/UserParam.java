package com.github.maojx0630.rbac.sys.param;

import com.github.maojx0630.rbac.common.valid.Password;
import com.github.maojx0630.rbac.common.valid.Phone;
import com.github.maojx0630.rbac.common.valid.Username;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 用户添加修改参数类
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-24 21:56 
 */
@Data
public class UserParam {

	@Null
	private Long id;

	@Username
	@NotBlank
	private String username;

	@Phone
	@NotBlank
	private String phone;

	@Password
	@NotBlank
	private String password;

	@NotNull
	private Long deptId;

	@Length
	private String remark;

}
