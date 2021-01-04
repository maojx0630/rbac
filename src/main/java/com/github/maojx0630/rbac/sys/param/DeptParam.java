package com.github.maojx0630.rbac.sys.param;


import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-22 19:15 
 */
@Data
@NoArgsConstructor
public class DeptParam {

	@Null
	private Long id;

	@NotBlank
	@Length(min = 2, max = 20)
	private String name;

	private Long parentId = GlobalStatic.TREE_ROOT;

	@NotNull
	private Integer seq;

	@Length(max = 150)
	private String remark;
}
