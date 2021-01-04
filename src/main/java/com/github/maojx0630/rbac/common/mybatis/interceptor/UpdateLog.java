package com.github.maojx0630.rbac.common.mybatis.interceptor;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 16:29 
 */
@Data
@TableName(value = "update_log")
public class UpdateLog {

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;

	@TableField(value = "uri")
	private String uri;

	@TableField(value = "method_name")
	private String methodName;

	@TableField(value = "param_json")
	private String paramJson;

	@TableField(value = "sql_info")
	private String sqlInfo;

	@TableField(value = "executor_user")
	private Long executorUser;

	@TableField(value = "executor_time")
	private Date executorTime;

	@TableField(value = "`table_name`")
	private String tableName;

	@TableField(value = "sql_type")
	private String sqlType;

	@TableField(value = "table_id")
	private String tableId;
}