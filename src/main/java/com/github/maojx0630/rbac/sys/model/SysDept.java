package com.github.maojx0630.rbac.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@TableName(value = "sys_dept")
@EqualsAndHashCode(callSuper = true)
public class SysDept extends ModelEntity {
	/**
	 * 部门名称
	 */
	@TableField(value = "name")
	@NotBlank(message = "部门名称不能为空")
	@Length(max = 20, min = 2, message = "部门名称长度应为2-20")
	private String name;

	/**
	 * 部门父级id
	 */
	@TableField(value = "parent_id")
	private Long parentId;

	/**
	 * 当前层级排序
	 */
	@TableField(value = "seq")
	@NotNull(message = "排序不能为空")
	private Integer seq;

	/**
	 * 备注
	 */
	@TableField(value = "remark")
	@Length(max = 150, message = "备注长度不能超过150")
	private String remark;

}