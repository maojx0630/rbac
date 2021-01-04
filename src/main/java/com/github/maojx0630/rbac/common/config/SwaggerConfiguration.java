package com.github.maojx0630.rbac.common.config;

import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.utils.SpringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-25 08:53 
 */
@Configuration
@ConditionalOnProperty(prefix = "customize", value = {"swagger"}, havingValue = "true")
@EnableSwagger2WebMvc
public class SwaggerConfiguration {

	@Bean
	public Docket createRestApi() {
		List<ResponseMessage> list = new ArrayList<>();
		for (StateEnum value : StateEnum.values()) {
			ResponseMessageBuilder builder=
					new ResponseMessageBuilder().code(value.getState()).message(value.getMsg());
			list.add(builder.build());
		}
		return new Docket(DocumentationType.SWAGGER_2)
				.globalResponseMessage(RequestMethod.GET, list)
				.globalResponseMessage(RequestMethod.POST, list)
				.globalResponseMessage(RequestMethod.PUT, list)
				.globalResponseMessage(RequestMethod.DELETE, list)
				.globalResponseMessage(RequestMethod.PATCH, list)
				.apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(SpringUtils.getBasePackage()))
				.paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("rehabilitation API Doc")
				.description("This is a restful api document of rehabilitation")
				.version("1.0").build();
	}



}
