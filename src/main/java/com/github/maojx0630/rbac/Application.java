package com.github.maojx0630.rbac;

import com.github.maojx0630.rbac.common.utils.IdUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 启动类
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 18:57
 */
@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		System.out.println(IdUtils.nextStr());
		System.out.println(IdUtils.nextStr());
		System.out.println(IdUtils.nextStr());
		System.out.println(IdUtils.nextStr());
		System.out.println(IdUtils.nextStr());
		SpringApplication.run(Application.class, args);
	}

}
