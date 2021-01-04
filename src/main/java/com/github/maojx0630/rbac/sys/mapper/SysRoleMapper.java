package com.github.maojx0630.rbac.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.maojx0630.rbac.sys.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

	long countByNameAndId(SysRole sysRole);

	void updateRole(SysRole sysRole);
}