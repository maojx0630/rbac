package com.github.maojx0630.rbac.common.mybatis;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.base.ModelEntity;
import com.github.maojx0630.rbac.common.config.login.UserUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 快速创建更新修改wrapper 实现指定部分字段更新
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-04 09:35
 */
public class WrapperUtils {

	/**
	 * 只更新不为null部分
	 */
	public static <T> UpdateWrapper<T> update(T t) {
		UpdateWrapper<T> wrapper = new UpdateWrapper<>();
		Arrays.asList(t.getClass().getDeclaredFields()).forEach(field -> {
			try {
				field.setAccessible(true);
				Object o = field.get(t);
				if (Objects.nonNull(o)) {
					wrapper.set(field.getName(), field.get(t));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		setUpdateInfo(wrapper, t);
		return wrapper;
	}

	/**
	 * 更新指定字段
	 */
	public static <T> UpdateWrapper<T> update(T t, String... columns) {
		UpdateWrapper<T> wrapper = new UpdateWrapper<>();
		Arrays.asList(columns).forEach(column -> wrapper.set(column, ReflectUtil.getFieldValue(t, column)));
		setUpdateInfo(wrapper, t);
		return wrapper;
	}

	/**
	 * 判断id对应的对象是否存在
	 * <br/>
	 * @param service 实体类对应的service
	 * @param id 主键
	 * @author MaoJiaXing
	 * @date 2020-12-28 15:19
	 */
	public static <S extends ServiceImpl<D, M>, D extends BaseMapper<M>, M> void checkRelevant(S service,
	                                                                                           Serializable id) {
		if (service.count(Wrappers.<M>query().eq("id", id)) != 1) {
			throw StateEnum.no_relevant_data.create();
		}
	}

	public static <S extends ServiceImpl<D, M>, D extends BaseMapper<M>, M> void checkRelevant(S service,
	                                                                                           Map<String,Object> map) {
		QueryWrapper<M> query = Wrappers.query();
		map.forEach(query::eq);
		if (service.count(query) != 1) {
			throw StateEnum.no_relevant_data.create();
		}
	}

	private static <T> void setUpdateInfo(UpdateWrapper<T> wrapper, T o) {
		if (o instanceof ModelEntity) {
			UserUtils.getOptUser().ifPresent(loginUserInfo -> wrapper.set("updateBy", loginUserInfo.getId()));
			wrapper.set("updateDate", new Date());
		}
	}
}
