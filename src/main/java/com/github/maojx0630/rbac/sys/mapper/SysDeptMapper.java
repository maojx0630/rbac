package com.github.maojx0630.rbac.sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.maojx0630.rbac.sys.model.DeptLevelTree;
import com.github.maojx0630.rbac.sys.model.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

	Long countByParentIdAndNameAndNotId(SysDept sysDept);

	List<DeptLevelTree> getAllDeptLevelDot();
}