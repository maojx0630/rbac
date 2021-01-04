package com.github.maojx0630.rbac.sys.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.sys.dto.LoginUserInfo;
import com.github.maojx0630.rbac.sys.mapper.SysUserMapper;
import com.github.maojx0630.rbac.sys.model.SysUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-24 21:51 
 */
@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

	/**
	 * 判断是否出现重复
	 * <br/>
	 * @author MaoJiaXing
	 * @date 2019-07-22 21:04
	 */
	private void checkExist(SysUser sysUser) {
		if (baseMapper.countByUsernameAndId(sysUser) > 0) {
			throw StateEnum.param_exist.create().insert("用户名");
		}
		if (baseMapper.countByPhoneAndId(sysUser) > 0) {
			throw StateEnum.param_exist.create().insert("手机号");
		}
	}

	/**
	 * 保存用户
	 */
	@Transactional
	public Long saveUser(SysUser sysUser) {
		checkExist(sysUser);
		sysUser.setPassword(BCrypt.hashpw(sysUser.getPassword()));
		if (baseMapper.insert(sysUser) != 1) {
			throw StateEnum.add_error.create();
		}
		return sysUser.getId();
	}

	/**
	 * 修改用户
	 */
	@Transactional
	public Long putUser(SysUser sysUser) {
		checkExist(sysUser);
		if (StrUtil.isNotBlank(sysUser.getPassword())) {
			sysUser.setPassword(BCrypt.hashpw(sysUser.getPassword()));
		}
		if (baseMapper.putUser(sysUser, sysUser.getId()) != 1) {
			throw StateEnum.update_error.create();
		}
		return sysUser.getId();
	}

	/**
	 * 删除用户
	 */
	@Transactional
	public Boolean removeUser(Long id) {
		if (!SqlHelper.retBool(baseMapper.deleteById(id))) {
			throw StateEnum.delete_error.create();
		}
		return true;
	}


	/**
	 * 获取需要静态缓存的用户信息
	 * @param id 用户id
	 * @return 用户信息
	 */
	public LoginUserInfo getLoginUserInfo(Long id) {
		LoginUserInfo info = new LoginUserInfo();
		info.setId(id);
		return info;
	}

}
