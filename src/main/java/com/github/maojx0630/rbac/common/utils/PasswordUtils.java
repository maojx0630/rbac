package com.github.maojx0630.rbac.common.utils;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import cn.hutool.crypto.symmetric.AES;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 密码工具类
 * 将密码HmacSHA512 哈希 使得密码长度一致后使用Argon2加密
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-28 10:31 
 */
public class PasswordUtils {

	//盐长度
	private static final int saltLength = 20;
	//hash长度
	private static final int hashLength = 40;
	//迭代次数
	private static final int iterations = 10;
	//内存大小
	private static final int memory = 4096;
	//并发线程数量
	private static final int parallelism = 4;

	private static final Charset UTF8 = StandardCharsets.UTF_8;
	private static final String UTF_8 = "UTF-8";
	//HmacSHA512与aes密钥，更改后之前密码都将不可用
	private static final byte[] AES_KEY = "ffb9b55525ff4d55973a6e3cb530cce2".getBytes(UTF8);
	private static final byte[] HMac_KEY = "4704b35b8de24c93ae99fc3a4fbd19e0".getBytes(UTF8);

	/**
	 * hash密码
	 * <br/>
	 * @param password 密码
	 * @return java.lang.String
	 * @author MaoJiaXing
	 * @date 2020-04-28 11:02
	 */
	public static String hash(String password) {
		String hash = getArgon2().hash(iterations, memory, parallelism,
				getHmac().digestHex(password, UTF_8).getBytes(UTF8));
		return getAes().encryptHex(hash, UTF8);
	}

	/**
	 * 校验密码是否正确
	 * <br/>
	 * @param hash hash后密码
	 * @param password 输入密码
	 * @return boolean
	 * @author MaoJiaXing
	 * @date 2020-04-28 11:02
	 */
	public static boolean verify(String hash, String password) {
		hash = getAes().decryptStr(hash);
		return getArgon2().verify(hash, getHmac().digestHex(password, UTF_8).getBytes(UTF8));
	}

	private static HMac getHmac() {
		return new HMac(HmacAlgorithm.HmacSHA512, HMac_KEY);
	}

	private static Argon2 getArgon2() {
		return Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, saltLength, hashLength);
	}

	private static AES getAes() {
		return SecureUtil.aes(AES_KEY);
	}
}
