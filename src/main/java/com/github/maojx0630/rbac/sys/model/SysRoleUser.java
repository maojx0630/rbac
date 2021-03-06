package com.github.maojx0630.rbac.sys.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@TableName(value = "sys_role_user")
public class SysRoleUser implements Serializable {

	@TableField(value = "role_id")
	private Long roleId;

	@TableField(value = "user_id")
	private Long userId;
}