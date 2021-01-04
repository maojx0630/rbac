package com.github.maojx0630.rbac.sys.model;

import com.github.maojx0630.rbac.common.utils.tree.TreeModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-23 10:32 
 */
@Data
public class DeptLevelTree implements TreeModel<DeptLevelTree> {

	@ApiModelProperty("部门主键")
	private Long id;

	@ApiModelProperty("部门名称")
	private String name;

	@ApiModelProperty("部门父级id")
	private Long parentId;

	@ApiModelProperty("当前层级排序")
	private Integer seq;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("子集")
	private List<DeptLevelTree> children;

}
