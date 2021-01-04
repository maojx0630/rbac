package com.github.maojx0630.rbac.common.utils;

import com.github.maojx0630.rbac.common.config.global.GlobalStatic;

import java.util.Base64;

public class UrlSafeBase64 {

    private UrlSafeBase64() {
    }   // don't instantiate

    /**
     * 编码字符串
     *
     * @param data 待编码字符串
     * @return 结果字符串
     */
    public static String encodeToString(String data) {
        return encodeToString(data.getBytes(GlobalStatic.UTF8));
    }

    /**
     * 编码数据
     *
     * @param data 字节数组
     * @return 结果字符串
     */
    public static String encodeToString(byte[] data) {
        return Base64.getUrlEncoder().encodeToString(data);
    }

    /**
     * 解码数据
     *
     * @param data 编码过的字符串
     * @return 原始数据
     */
    public static byte[] decode(String data) {
        return Base64.getUrlDecoder().decode(data);
    }
}
