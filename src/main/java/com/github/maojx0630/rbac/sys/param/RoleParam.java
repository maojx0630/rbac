package com.github.maojx0630.rbac.sys.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-03-23 16:08 
 */
@Data
public class RoleParam {

	@NotBlank
	private String name;

	@NotNull
	private Integer seq;

	@Length(max = 150)
	private String remark;

}
