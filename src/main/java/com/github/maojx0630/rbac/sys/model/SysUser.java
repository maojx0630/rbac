package com.github.maojx0630.rbac.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@TableName(value = "sys_user")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends ModelEntity {
	/**
	 * 用户名
	 */
	@TableField(value = "username")
	private String username;

	/**
	 * 用户手机号
	 */
	@TableField(value = "phone")
	private String phone;

	/**
	 * 用户密码
	 */
	@TableField(value = "password")
	private String password;

	/**
	 * 部门id
	 */
	@TableField(value = "dept_id")
	private Long deptId;

	/**
	 * 0:正常,1:冻结
	 */
	@TableField(value = "status")
	private String status;

	/**
	 * 备注
	 */
	@TableField(value = "remark")
	private String remark;

}