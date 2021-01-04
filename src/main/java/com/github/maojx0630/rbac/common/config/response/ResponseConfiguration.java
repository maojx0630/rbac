package com.github.maojx0630.rbac.common.config.response;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.maojx0630.rbac.common.config.global.MyProperty;
import com.github.maojx0630.rbac.common.config.response.log.HomeLogInterceptor;
import com.github.maojx0630.rbac.common.config.response.serialize.DictSerializeFilter;
import com.github.maojx0630.rbac.common.config.login.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 统一返回配置
 * <br/>
 * @author MaoJiaXing
 * @date 2019-07-21 18:57
 */
@Configuration
public class ResponseConfiguration implements WebMvcConfigurer {

	@Autowired
	private UserUtils userUtils;

	@Autowired
	private MyProperty myProperty;

	/**
	 * 添加fastjson作为返回值转换器
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.getFastJsonConfig().setSerializeFilters(new DictSerializeFilter());
		converter.getFastJsonConfig().setSerializerFeatures(
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty);
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON);
		supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
		supportedMediaTypes.add(MediaType.APPLICATION_PDF);
		supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
		supportedMediaTypes.add(MediaType.APPLICATION_XML);
		supportedMediaTypes.add(MediaType.IMAGE_GIF);
		supportedMediaTypes.add(MediaType.IMAGE_JPEG);
		supportedMediaTypes.add(MediaType.IMAGE_PNG);
		supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.TEXT_XML);
		converter.setSupportedMediaTypes(supportedMediaTypes);
		converters.clear();
		converters.add(converter);
	}


	/**
	 * 添加日志拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(new HomeLogInterceptor(userUtils));
		registration.addPathPatterns("/**");
		if (myProperty.isSwagger()) {
			registration.excludePathPatterns(myProperty.getSwaggerUrl());
		}
		registration.order(Ordered.HIGHEST_PRECEDENCE);
	}

}
