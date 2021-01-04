package com.github.maojx0630.rbac.common.utils.tree;

import java.util.List;

/**
 * 树结构 用来转为树结构
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-23 11:11 
 */
public interface TreeModel<T> extends CascadeModel {

	/**
	 *  用于将子集封装进去
	 * <br/>
	 * @param children 放入子集
	 * @author MaoJiaXing
	 * @date 2019-07-23 19:06
	 */
	void setChildren(List<T> children);

	/**
	 * 获取子集
	 * <br/>
	 * @author MaoJiaXing
	 * @date 2020-12-07 14:27
	 */
	List<T> getChildren();

	/**
	 * 获取排序
	 * <br/>
	 * @return java.lang.Integer
	 * @author MaoJiaXing
	 * @date 2019-07-23 19:07
	 */
	Integer getSeq();

}
