package com.github.maojx0630.rbac.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@TableName(value = "sys_acl")
@EqualsAndHashCode(callSuper = true)
public class SysAcl extends ModelEntity {
	/**
	 * 权限名称
	 */
	@TableField(value = "name")
	private String name;

	/**
	 * 父级id
	 */
	@TableField(value = "parent_id")
	private Long parentId;

	/**
	 * 1:菜单,2:按钮,3:其他
	 */
	@TableField(value = "type")
	private Integer type;

	/**
	 * 0:正常,1:冻结
	 */
	@TableField(value = "status")
	private Integer status;

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

	/**
	 * 后台资源路径,请求的url可以填写ant表达式
	 */
	@TableField(value = "url")
	private String url;

	/**
	 * 前台资源路径
	 */
	@TableField(value = "path")
	private String path;

	/**
	 * 资源权限标识
	 */
	@TableField(value = "permission")
	private String permission;

	/**
	 * 组件路径
	 */
	@TableField(value = "component")
	private String component;

	/**
	 * 请求类型 get post put delete
	 */
	@TableField(value = "method")
	private String method;

	/**
	 * 图标
	 */
	@TableField(value = "icon")
	private String icon;

}