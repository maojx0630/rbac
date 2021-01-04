package com.github.maojx0630.rbac.common.config;

import com.alibaba.fastjson.JSON;
import com.github.maojx0630.rbac.common.config.global.MyProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.TimeZone;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 18:04 
 */
@Slf4j
@Configuration
public class SpringConfiguration {

	@Autowired
	private MyProperty myProperty;

	/**
	 * 加载数据库连接，防止第一次访问时再加载导致卡顿
	 */
	@Bean
	public ApplicationRunner runner(DataSource dataSource) {
		return args -> {
			Connection connection = dataSource.getConnection();
			DatabaseMetaData data = connection.getMetaData();
			log.info("数据库连接成功 数据库 [{} : {}] 驱动 [{} : {}]", data.getDatabaseProductName(),
					data.getDatabaseProductVersion(), data.getDriverName(), data.getDriverVersion());
		};
	}

	@PostConstruct
	void setDefaultTimezone() {
		TimeZone timeZone = TimeZone.getTimeZone(myProperty.getTimeZone());
		TimeZone.setDefault(timeZone);
		JSON.defaultTimeZone=timeZone;
	}
}
