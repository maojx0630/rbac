package com.github.maojx0630.rbac.common.config.response.serialize;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;

import java.util.Objects;

/**
 * 将字典值自动转为label的执行器
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-01 14:44 
 */
public class DictSerializeFilter implements ContextValueFilter {

	@Override
	public Object process(BeanContext beanContext, Object o, String name, Object value) {
		if(Objects.isNull(beanContext)){
			return value;
		}
		DictLabel dictLabel = beanContext.getAnnation(DictLabel.class);
		if (dictLabel != null) {
			String type = dictLabel.value();
			if (StrUtil.isNotBlank(type)) {
				return "type_type_type";
			}
		}
		return value;
	}

}
