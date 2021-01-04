package com.github.maojx0630.rbac.common.config.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 10:02 
 */
@Component
public class GlobalStaticComponent {

	static String logicDeleteValue;

	static String logicNotDeleteValue;

	@Value("${mybatis-plus.global-config.db-config.logic-delete-value}")
	public void setLogicDeleteValue(String logicDeleteValue) {
		GlobalStaticComponent.logicDeleteValue = logicDeleteValue;
	}

	@Value("${mybatis-plus.global-config.db-config.logic-not-delete-value}")
	public void setLogicNotDeleteValue(String logicNotDeleteValue) {
		GlobalStaticComponent.logicNotDeleteValue = logicNotDeleteValue;
	}

}
