package com.github.maojx0630.rbac.common.mybatis.interceptor;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.maojx0630.rbac.common.config.global.MyProperty;
import com.github.maojx0630.rbac.common.config.login.UserUtils;
import com.github.maojx0630.rbac.common.utils.RequestUtils;
import com.github.maojx0630.rbac.sys.dto.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 将insert 和 update 记录保存
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 14:32 
 */
@Slf4j
@Component
@Intercepts(@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,
		Object.class}))
public class UpdateInterceptor implements Interceptor {

	@Autowired
	private MyProperty myProperty;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (!myProperty.isUpdateLog()) {
			return invocation.proceed();
		}
		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		if (withPackageName(ms.getId())) {
			return invocation.proceed();
		}
		Optional<LoginUserInfo> optUser = UserUtils.getOptUser();
		if (!optUser.isPresent()) {
			return invocation.proceed();
		}
		long start = System.currentTimeMillis();
		LoginUserInfo user = optUser.get();
		UpdateLog updateLog = new UpdateLog();
		updateLog.setExecutorTime(new Date());
		updateLog.setExecutorUser(user.getId());
		Object parameter = invocation.getArgs()[1];
		SqlCommandType type = ms.getSqlCommandType();
		String sql = ms.getBoundSql(parameter).getSql();
		updateLog.setMethodName(ms.getId());
		updateLog.setSqlInfo(sql);
		updateLog.setParamJson(JSON.toJSONString(parameter));
		updateLog.setSqlType(type.name());
		updateLog.setTableName(getTableNameBySql(sql, type));
		try {
			updateLog.setUri(RequestUtils.getRequest().getRequestURI());
		} catch (Exception e) {
			updateLog.setUri(e.getMessage());
		}
		long startTime = System.currentTimeMillis() - start;
		Object proceed = invocation.proceed();
		long end = System.currentTimeMillis();
		updateLog.setTableId(getIdByParam(parameter));
		StaticUpdateLog.insert(updateLog);
		log.info("插入操作日志耗时[{}]毫秒", System.currentTimeMillis() - end + startTime);
		return proceed;
	}

	//防止出现循环插入 看是不是UpdateLogMapper类执行的
	private boolean withPackageName(String id) {
		Class<UpdateLogMapper> aClass = UpdateLogMapper.class;
		String name = aClass.getPackage().getName();
		return id.startsWith(name);
	}

	//获取table name
	private String getTableNameBySql(String sql, SqlCommandType type) {
		try {
			String str = sql.trim().toLowerCase();
			String[] strings = str.split(" ");
			List<String> list = new ArrayList<>(strings.length);
			for (String string : strings) {
				if (StrUtil.isNotBlank(str)) {
					list.add(string);
				}
			}
			if (type == SqlCommandType.INSERT) {
				return list.get(2);
			} else if (type == SqlCommandType.UPDATE) {
				return list.get(1);
			} else if (type == SqlCommandType.DELETE) {
				return list.get(2);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.warn("get table name error , exception msg [{}] , sql : [{}]", e.getMessage(),
					sql);
			return null;
		}
	}

	//分析参数准备获取id
	private String getIdByParam(Object param) {
		if (param instanceof MapperMethod.ParamMap) {
			MapperMethod.ParamMap map = (MapperMethod.ParamMap) param;
			Set set = map.entrySet();
			for (Object o : set) {
				if (o instanceof Map.Entry) {
					Map.Entry entry = (Map.Entry) o;
					if ("id".equals(entry.getKey()) && entry.getValue() instanceof String) {
						return entry.getValue().toString();
					}
					Object value = entry.getValue();
					String idValue = getIdValue(value);
					if (StrUtil.isNotBlank(idValue)) {
						return idValue;
					}
				}
			}
		} else {
			return getIdValue(param);
		}
		return null;
	}

	//获取id的值
	private String getIdValue(Object o) {
		Class<?> aClass = o.getClass();
		Field[] fields = ReflectUtil.getFields(aClass);
		Field valueField = null;
		for (Field field : fields) {
			field.setAccessible(true);
			TableId annotation = field.getAnnotation(TableId.class);
			if (annotation != null) {
				valueField = field;
				break;
			}
		}
		try {
			if (valueField == null) {
				return null;
			}
			return valueField.get(o).toString();
		} catch (Exception e) {
			log.warn("get id value error , exception msg [{}]", e.getMessage());
			return null;
		}
	}
}
