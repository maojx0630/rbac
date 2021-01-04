package com.github.maojx0630.rbac.common.config.global;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "customize")
public class MyProperty {

	//是否开始update日志
	private boolean updateLog;

	//是否开启swagger
	private boolean swagger;

	//文件上传路径 最后必须携带/或\
	private String fileBasePath;

	//启动类所在包，默认获取SpringBootApplication注解类的包
	private String basePackage = "com.github.maojx0630";

	//时区
	private String timeZone = "Asia/Shanghai";

	//配置忽略拦截的路径
	private List<String> ignoreUrl= Collections.emptyList();

	//swagger忽略的路径
	private List<String> swaggerUrl= Arrays.asList("/swagger-resources/**", "/webjars/**", "/v2/**",
			"/doc.html/**");
}
