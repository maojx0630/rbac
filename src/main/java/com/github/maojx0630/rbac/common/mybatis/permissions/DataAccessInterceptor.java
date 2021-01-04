package com.github.maojx0630.rbac.common.mybatis.permissions;

import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 查询拦截器 用来实现数据权限过滤
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 14:32 
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class}), @Signature(type = Executor.class, method = "query", args =
		{MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class,
				BoundSql.class})})
public class DataAccessInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		Optional<DataAccess> optional = checkAnn(ms.getId());
		Object parameter = args[1];
		RowBounds rowBounds = (RowBounds) args[2];
		ResultHandler resultHandler = (ResultHandler) args[3];
		Executor executor = (Executor) invocation.getTarget();
		CacheKey cacheKey;
		BoundSql boundSql;
		if (args.length == 4) {
			boundSql = ms.getBoundSql(parameter);
			cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
		} else {
			cacheKey = (CacheKey) args[4];
			boundSql = (BoundSql) args[5];
		}
		if(optional.isPresent()){
			ReflectUtil.setFieldValue(boundSql,"sql",getSql(optional.get(),boundSql.getSql()));
			return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
		}else{
			return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
		}
	}

	private String getSql(DataAccess dataAccess,String sql){
		return sql;
	}

	private Optional<DataAccess> checkAnn(String id){
		try {
			String className = id.substring(0, id.lastIndexOf("."));
			String methodName = id.substring(id.lastIndexOf(".") + 1);
			Method method = ReflectUtil.getMethodByName(Class.forName(className), methodName);
			return Optional.ofNullable(method.getAnnotation(DataAccess.class));
		}catch (Exception e){
			return Optional.empty();
		}
	}
}
