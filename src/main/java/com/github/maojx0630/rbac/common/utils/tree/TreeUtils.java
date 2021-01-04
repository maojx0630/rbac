package com.github.maojx0630.rbac.common.utils.tree;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-23 11:10 
 */
public class TreeUtils {

	/**
	 * 转化为树形结构
	 */
	public static <T extends TreeModel<T>> List<T> toTree(List<T> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<>();
		}
		MultiValueMap<Long, T> multiValueMap = new LinkedMultiValueMap<>();
		List<T> rootList = new LinkedList<>();
		list.forEach(obj -> {
			multiValueMap.add(obj.getParentId(), obj);
			if (GlobalStatic.TREE_ROOT.equals(obj.getParentId())) {
				rootList.add(obj);
			}
		});
		rootList.sort(Comparator.comparingInt(TreeModel::getSeq));
		convertTree(rootList, multiValueMap);
		return rootList;
	}

	//获取指定id的子集id集合
	public static <T extends CascadeModel> Set<Long> getCascadeId(List<T> list, Long id) {
		if (CollectionUtils.isEmpty(list)) {
			return new HashSet<>();
		}
		MultiValueMap<Long, T> multiValueMap = new LinkedMultiValueMap<>();
		List<T> linkedList = new LinkedList<>();
		Set<Long> allSet = new HashSet<>();
		list.forEach(obj -> {
			multiValueMap.add(obj.getParentId(), obj);
			if (id.equals(obj.getParentId())) {
				linkedList.add(obj);
				allSet.add(obj.getId());
			}
		});
		cascadeTree(allSet, linkedList, multiValueMap);
		allSet.add(id);
		return allSet;
	}

	/**
	 * 传入不是树的集合获取指定id的子集
	 * <br/>
	 * @param list 集合
	 * @param id 指定id
	 * @return java.util.List<T>
	 * @author MaoJiaXing
	 * @date 2020-12-07 14:32
	 */
	public static <T extends TreeModel<T>> List<T> getTreeById(List<T> list,Long id){
		List<T> tree = toTree(list);
		return recursiveTreeById(tree,id);
	}

	/**
	 * 传入树的集合获取指定id的子集
	 * <br/>
	 * @param list 集合
	 * @param id 指定id
	 * @return java.util.List<T>
	 * @author MaoJiaXing
	 * @date 2020-12-07 14:32
	 */
	public static <T extends TreeModel<T>> List<T> recursiveTreeById(List<T> list,Long id){
		for (T t : list) {
			if(t.getId().equals(id)){
				return t.getChildren();
			}else{
				List<T> children = t.getChildren();
				List<T> tList;
				if(children==null||children.isEmpty()){
					continue;
				}else {
					tList=recursiveTreeById(children,id);
				}
				if(!(tList==null||tList.isEmpty())){
					return tList;
				}
			}
		}
		return null;
	}

	private static <T extends CascadeModel> void cascadeTree(Set<Long> allSet, List<T> list, MultiValueMap<Long,
			T> multiValueMap) {
		list.forEach(obj -> {
			allSet.add(obj.getId());
			List<T> tList = multiValueMap.get(obj.getId());
			if (CollectionUtils.isNotEmpty(tList)) {
				cascadeTree(allSet, tList, multiValueMap);
			}
		});
	}

	private static <T extends TreeModel<T>> void convertTree(List<T> list, MultiValueMap<Long, T> multiValueMap) {
		list.forEach(obj -> {
			List<T> tList = multiValueMap.get(obj.getId());
			if (CollectionUtils.isNotEmpty(tList)) {
				tList.sort(Comparator.comparingInt(TreeModel::getSeq));
				obj.setChildren(tList);
				convertTree(tList, multiValueMap);
			}
		});
	}
}
