package com.github.maojx0630.rbac.sys.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.utils.tree.TreeUtils;
import com.github.maojx0630.rbac.sys.mapper.SysDeptMapper;
import com.github.maojx0630.rbac.sys.model.DeptLevelTree;
import com.github.maojx0630.rbac.sys.model.SysDept;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 20:57 
 */
@Service
public class SysDeptService extends ServiceImpl<SysDeptMapper, SysDept> {

	/**
	 * 保存部门
	 * <br/>
	 * @param sysDept 保存部门所需参数
	 * @return java.lang.Boolean
	 * @author MaoJiaXing
	 * @date 2019-07-22 21:03
	 */
	@Transactional
	public Long saveDept(SysDept sysDept) {
		if (checkExist(sysDept)) {
			throw StateEnum.param_exist.create().insert("部门名称");
		}
		if (baseMapper.insert(sysDept) == 1) {
			return sysDept.getId();
		} else {
			throw StateEnum.update_error.create();
		}
	}

	/**
	 * 根据ParentId和Name判断 可通过id过滤
	 * <br/>
	 * @param sysDept ParentId Name id
	 * @return boolean
	 * @author MaoJiaXing
	 * @date 2019-07-22 21:04
	 */
	private boolean checkExist(SysDept sysDept) {
		return baseMapper.countByParentIdAndNameAndNotId(sysDept) > 0;
	}

	/**
	 * 修改dept
	 * <br/>
	 * @param sysDept 要修改的参数
	 * @return java.lang.Long
	 * @author MaoJiaXing
	 * @date 2019-07-24 17:17
	 */
	@Transactional
	public Long updateDept(SysDept sysDept) {
		if (checkExist(sysDept)) {
			throw StateEnum.param_exist.create().insert("部门名称");
		}
		baseMapper.updateById(sysDept);
		return sysDept.getId();
	}

	/**
	 * 获取树形结构
	 * <br/>
	 * @return java.util.List<com.zfei.rehabilitation.sys.model.DeptLevelTree>
	 * @author MaoJiaXing
	 * @date 2019-07-23 19:54
	 */
	public List<DeptLevelTree> getTree() {
		List<DeptLevelTree> list = TreeUtils.toTree(baseMapper.getAllDeptLevelDot());
		if (CollectionUtils.isEmpty(list)) {
			throw StateEnum.no_data.create();
		} else {
			return list;
		}
	}

	/**
	 * 级联删除所有子部门
	 * <br/>
	 * @param id 将要删除的id
	 * @return java.lang.Integer
	 * @author MaoJiaXing
	 * @date 2019-07-24 21:52
	 */
	@Transactional
	public Integer deleteAllCascade(Long id) {
		List<DeptLevelTree> list = baseMapper.getAllDeptLevelDot();
		Set<Long> set = TreeUtils.getCascadeId(list, id);
		return baseMapper.deleteBatchIds(set);
	}
}
