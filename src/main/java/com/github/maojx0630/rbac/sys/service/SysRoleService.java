package com.github.maojx0630.rbac.sys.service;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.mybatis.page.PageUtils;
import com.github.maojx0630.rbac.sys.mapper.SysRoleMapper;
import com.github.maojx0630.rbac.sys.model.SysRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 20:58 
 */
@Service
public class SysRoleService extends ServiceImpl<SysRoleMapper, SysRole> {

	@Transactional
	public Long updateRole(SysRole sysRole) {
		checkExistName(sysRole);
		baseMapper.updateRole(sysRole);
		return sysRole.getId();
	}

	/**
	 * 判断角色名是否重复
	 * <br/>
	 * @param sysRole
	 * @return boolean
	 * @author MaoJiaXing
	 * @date 2020-03-23 18:18
	 */
	private void checkExistName(SysRole sysRole) {
		if (baseMapper.countByNameAndId(sysRole) > 0) {
			throw StateEnum.param_exist.create().insert("角色名称");
		}
	}

	/**
	 * 获取角色列表
	 * <br/>
	 * @param sysRole
	 * @return com.baomidou.mybatisplus.core.metadata.IPage<com.zfei.rehabilitation.sys.model.SysRole>
	 * @author MaoJiaXing
	 * @date 2020-03-23 18:40
	 */
	public IPage<SysRole> getList(SysRole sysRole) {
		LambdaQueryWrapper<SysRole> wrapper = Wrappers.lambdaQuery();
		if (StrUtil.isNotBlank(sysRole.getName())) {
			wrapper.like(SysRole::getName, sysRole.getName());
		}
		if (StrUtil.isNotBlank(sysRole.getStatus())) {
			wrapper.eq(SysRole::getStatus, sysRole.getStatus());
		}
		wrapper.orderByAsc(SysRole::getSeq);
		return baseMapper.selectPage(PageUtils.get(), wrapper);
	}

	/**
	 * 保存角色信息
	 * <br/>
	 * @param sysRole
	 * @return com.zfei.rehabilitation.sys.model.SysRole
	 * @author MaoJiaXing
	 * @date 2020-03-23 19:15
	 */
	public SysRole saveRole(SysRole sysRole) {
		checkExistName(sysRole);
		save(sysRole);
		return sysRole;
	}
}
