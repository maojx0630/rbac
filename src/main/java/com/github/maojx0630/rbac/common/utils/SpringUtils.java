package com.github.maojx0630.rbac.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import com.github.maojx0630.rbac.common.config.global.MyProperty;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.mybatis.page.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * spring工具类
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-13 14:39
 */
@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext CONTEXT;

	private static String BASE_PACKAGE;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		CONTEXT = applicationContext;
		if (Objects.nonNull(CONTEXT)) {
			log.info("ApplicationContext 已经成功注入！");
			MyProperty myProperty=CONTEXT.getBean(MyProperty.class);
			if(!("com.github.maojx0630".equals(myProperty.getBasePackage()))){
				BASE_PACKAGE=myProperty.getBasePackage();
				log.info("base_package 使用了配置属性 {}",BASE_PACKAGE);
				return;
			}
			try {
				String[] beanNamesForAnnotation = CONTEXT.getBeanNamesForAnnotation(SpringBootApplication.class);
				Object bean = CONTEXT.getBean(beanNamesForAnnotation[0]);
				BASE_PACKAGE=bean.getClass().getPackage().getName();
			}catch (Exception ignored){
			}
			if(StrUtil.isNotBlank(BASE_PACKAGE)){
				log.info("base_package 已经获取 {}",BASE_PACKAGE);
			}else{
				BASE_PACKAGE="com.github.maojx0630";
				log.error("base_package 获取失败 自动填充为 com.github.maojx0630");
			}
		} else {
			log.error("ApplicationContext 注入失败，SpringUtils getBean已不可用！");
		}
	}

	/**
	 * 复制bean
	 * <br/>
	 * @param k 原始数据
	 * @return V 复制后对象
	 * @author MaoJiaXing
	 * @date 2019-07-22 19:39
	 */
	public static <K, V> V copy(K k, Class<V> aClass) {
		V v;
		try {
			v = aClass.newInstance();
		} catch (Exception e) {
			throw StateEnum.default_structure.create();
		}
		BeanUtil.copyProperties(k, v);
		return v;
	}

	/**
	 * copy集合
	 * <br/>
	 * @param kList 被复制的集合
	 * @param aClass 要转换的类
	 * @return java.util.List<V>
	 * @author MaoJiaXing
	 * @date 2020-12-08 14:36
	 */
	public static <K, V> List<V> copy(List<K> kList, Class<V> aClass) {
		List<V> list=new ArrayList<>();
		if (kList==null||kList.isEmpty()){
			return list;
		}
		for (K k : kList) {
			V v;
			try {
				v = aClass.newInstance();
			} catch (Exception e) {
				throw StateEnum.default_structure.create();
			}
			BeanUtil.copyProperties(k, v);
			list.add(v);
		}
		return list;
	}

	/**
	 * copy page
	 * <br/>
	 * @param page 被转换类的page对象
	 * @param aClass 转换成的类
	 * @return java.util.List<V>
	 * @author MaoJiaXing
	 * @date 2020-12-08 14:37
	 */
	public static <K, V> Page<V> copy(Page<K> page, Class<V> aClass) {
		List<V> copy = copy(page.getRecords(), aClass);
		Page<V> vPage=new Page<>();
		vPage.setRecords(copy);
		vPage.setTotal(page.getTotal());
		vPage.setSize(page.getSize());
		vPage.setCurrent(page.getCurrent());
		return vPage;
	}



	/**
	 * 获取bean
	 * <br/>
	 * @param clazz 类型
	 * @return T
	 * @author MaoJiaXing
	 * @date 2020-04-13 14:55
	 */
	public static <T> T getBean(Class<T> clazz) {
		return CONTEXT.getBean(clazz);
	}

	/**
	 * 获取指定类型的集合
	 * <br/>
	 * @param clazz 类型
	 * @return java.util.List<T>
	 * @author MaoJiaXing
	 * @date 2020-12-25 15:52
	 */
	public static <T> List<T> getBeanList(Class<T> clazz){
		Map<String, T> map = CONTEXT.getBeansOfType(clazz);
		if(null==map||map.isEmpty()){
			return Collections.emptyList();
		}else{
			List<T> list=new LinkedList<>();
			map.forEach((str,t)-> list.add(t));
			return list;
		}
	}

	/**
	 * 将param转为model类
	 * <br/>
	 * @param o param
	 * @param id 主键
	 * @param clazz model的类型
	 * @return T
	 * @author MaoJiaXing
	 * @date 2020-12-28 11:29
	 */
	public static <T extends ModelEntity> T copy(Object o, Long id, Class<T> clazz){
		T copy = copy(o, clazz);
		copy.setId(id);
		return copy;
	}

	//获取启动类包名
	public static String getBasePackage(){
		return BASE_PACKAGE;
	}
}
