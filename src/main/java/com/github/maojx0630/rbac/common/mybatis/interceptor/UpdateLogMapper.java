package com.github.maojx0630.rbac.common.mybatis.interceptor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-11-23 16:29 
 */
@Mapper
public interface UpdateLogMapper extends BaseMapper<UpdateLog> {
}