package com.github.maojx0630.rbac.common.utils;

import com.alibaba.fastjson.JSON;
import com.github.maojx0630.rbac.common.config.response.result.ResponseResultState;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;

public class ResponseUtils {
    private ResponseUtils() {
    }

    public static void response(HttpServletResponse response, ResponseResultState state){
        response.setHeader("Content-type", "application/Json;charset=UTF-8");
        try {
            response.getOutputStream().write(JSON.toJSONString(state).getBytes(StandardCharsets.UTF_8));
        }catch (Exception ignored){
        }
    }

}
