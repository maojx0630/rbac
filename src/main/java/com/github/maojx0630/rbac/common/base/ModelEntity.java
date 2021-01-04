package com.github.maojx0630.rbac.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 *  model父类 继承后 调用MybatisUtils可以自动添加update信息
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-04 09:05 
 */
public abstract class ModelEntity implements ModelDeleteInfo{

	@TableId(value = "id", type = IdType.ASSIGN_ID)
	protected Long id;

	@TableField(value = "del_flag",fill = FieldFill.INSERT)
	protected String delFlag;

	@TableField(value = "create_by",fill = FieldFill.INSERT)
	protected Long createBy;

	@TableField(value = "update_by",fill = FieldFill.INSERT_UPDATE)
	protected Long updateBy;

	@TableField(value = "create_date",fill = FieldFill.INSERT)
	protected Date createDate;

	@TableField(value = "update_date",fill = FieldFill.INSERT_UPDATE)
	protected Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
