package com.github.maojx0630.rbac.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "sys_role")
@EqualsAndHashCode(callSuper = true)
public class SysRole extends ModelEntity {
	/**
	 * 角色名称
	 */
	@TableField(value = "name")
	private String name;

	/**
	 * 0:正常,1:冻结
	 */
	@TableField(value = "status")
	private String status;

	/**
	 * 排序
	 */
	@TableField(value = "seq")
	private Integer seq;

	/**
	 * 备注
	 */
	@TableField(value = "remark")
	private String remark;
}