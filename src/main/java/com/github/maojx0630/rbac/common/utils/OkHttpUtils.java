package com.github.maojx0630.rbac.common.utils;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

/**
 *
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-03 15:41 
 */
public abstract class OkHttpUtils {

	/**
	 * 构造http请求 okhttp
	 * <br/>
	 * @return okhttp3.OkHttpClient
	 * @author MaoJiaXing
	 * @date 2020-12-14 14:28
	 */
	public static OkHttpClient getClient(){
		return getClient(new OkHttpClient().newBuilder());
	}

	/**
	 * 构造http请求 okhttp
	 * <br/>
	 * @param builder 自定义部分参数
	 * @return okhttp3.OkHttpClient
	 * @author MaoJiaXing
	 * @date 2020-12-14 14:29
	 */
	public static OkHttpClient getClient(OkHttpClient.Builder builder){
		return builder.sslSocketFactory(getSSLSocketFactory(),
				getX509TrustManager()).build();
	}

	/**
	 * 直接获得call 发送普通无自定义参数https请求
	 * <br/>
	 * @param request 请求参数
	 * @return okhttp3.Call
	 * @author MaoJiaXing
	 * @date 2020-12-14 14:29
	 */
	public static Call getCall(Request request){
		return getClient().newCall(request);
	}

	private static SSLSocketFactory getSSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, getTrustManager(), new SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//获取TrustManager
	private static TrustManager[] getTrustManager() {
		return new TrustManager[]{getX509TrustManager()};
	}

	private static X509TrustManager getX509TrustManager() {
		return new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[]{};
			}
		};

	}

}
