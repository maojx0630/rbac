package com.github.maojx0630.rbac.common.utils.tree;

/**
 * 树结构 用来级联获取
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-24 21:26 
 */
public interface CascadeModel {

	/**
	 * 获取级别
	 * <br/>
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2019-07-23 19:06
	 */
	Long getParentId();

	/**
	 * 获取id
	 * <br/>
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2019-07-23 19:06
	 */
	Long getId();
}
