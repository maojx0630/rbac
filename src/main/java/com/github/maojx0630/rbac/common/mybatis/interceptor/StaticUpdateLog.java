package com.github.maojx0630.rbac.common.mybatis.interceptor;

import com.github.maojx0630.rbac.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * 修改记录保存
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 17:16 
 */
@Slf4j
public class StaticUpdateLog {

	private static final UpdateLogMapper mapper = SpringUtils.getBean(UpdateLogMapper.class);

	static void insert(UpdateLog updateLog) {
		mapper.insert(updateLog);

	}
}
