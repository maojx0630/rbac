package com.github.maojx0630.rbac.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.maojx0630.rbac.common.mybatis.page.Page;
import com.github.maojx0630.rbac.sys.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

	Long countByUsernameAndId(SysUser sysUser);

	Long countByPhoneAndId(SysUser sysUser);

	int putUser(@Param("updated") SysUser updated, @Param("id") Long id);

	Page<SysUser> getUserList(Page<SysUser> page);

}