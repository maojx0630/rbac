package com.github.maojx0630.rbac.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.github.maojx0630.rbac.common.config.login.UserUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 更新添加前赋值
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-04 09:17
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Value("${mybatis-plus.global-config.db-config.logic-delete-field}")
	private String delName;

	@Value("${mybatis-plus.global-config.db-config.logic-not-delete-value}")
	private String delValue;

	@Override
	public void insertFill(MetaObject mate) {
		if (mate.hasGetter("createBy")) {
			UserUtils.getOptUser().ifPresent(loginUserInfo -> this.setFieldValByName("createBy",
					loginUserInfo.getId(), mate));
		}
		if (mate.hasGetter("createDate")) {
			this.setFieldValByName("createDate", new Date(), mate);
		}
		if(mate.hasGetter(delName)){
			this.setFieldValByName(delName,delValue,mate);
		}
		updateFill(mate);
	}

	@Override
	public void updateFill(MetaObject mate) {
		if (mate.hasGetter("createBy")) {
			UserUtils.getOptUser().ifPresent(loginUserInfo -> this.setFieldValByName("updateBy",
					loginUserInfo.getId(), mate));
		}
		if (mate.hasGetter("updateDate")) {
			this.setFieldValByName("updateDate", new Date(), mate);
		}
	}
}
