package com.github.maojx0630.rbac.common.utils.rsa;


import com.github.maojx0630.rbac.common.config.global.GlobalStatic;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 * rsa工具类
 * <br/>
 * @author MaoJiaXing
 * @date 2020-12-07 14:18
 */
@SuppressWarnings("all")
public class RsaUtils {

	/**
	 * 生成一堆密钥
	 * @return Rsa对象，保存一对密钥
	 */
	public static RsaEntity createKeyPair() throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		//生成密钥对mvnc
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		//获取公钥
		PublicKey publicKey = keyPair.getPublic();
		//获取私钥
		PrivateKey privateKey = keyPair.getPrivate();
		return new RsaEntity(publicKey, privateKey);
	}

	/**
	 * RSA加密
	 * @param str 要加密参数
	 * @param publicKey 公钥
	 * @return 密文
	 */
	public static String encryptWithRSA(String str, String publicKey) throws Exception {

		//获取一个加密算法为RSA的加解密器对象cipher。
		Cipher cipher = Cipher.getInstance("RSA");
		//设置为加密模式,并将公钥给cipher。
		cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
		//获得密文
		byte[] secret = cipher.doFinal(str.getBytes());
		//进行Base64编码
		return Base64.getEncoder().encodeToString(secret);
	}

	/**
	 * RSA解密
	 * @param secret 密文参数
	 * @param privateKey 私钥
	 * @return 解密后字符串
	 */
	public static String decryptWithRSA(String secret, String privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		//传递私钥，设置为解密模式。
		cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
		//解密器解密由Base64解码后的密文,获得明文字节数组
		byte[] b = cipher.doFinal(Base64.getDecoder().decode(secret));
		//转换成字符串
		return new String(b);

	}

	/**
	 * 将公钥转化为公钥对象
	 */
	public static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 将私钥转化为私钥对象
	 */
	public static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes = Base64.getDecoder().decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * RSA签名
	 *
	 * @param content 待签内容
	 * @param privateKey 私钥
	 * @return 签名
	 */
	public static String sign(String content, String privateKey) throws Exception {
		//用私钥对信息生成数字签名
		Signature stool = Signature.getInstance("MD5WithRSA");
		stool.initSign(getPrivateKey(privateKey));
		stool.update(content.getBytes(GlobalStatic.UTF8));
		byte[] data = stool.sign();
		return Base64.getEncoder().encodeToString(data);
	}

	/**
	 * 校验签名
	 *
	 * @param content 待验内容
	 * @param signature 签名
	 * @param publicKey 公钥
	 * @return 是否有效签名
	 */
	public static boolean verify(String content, String signature, String publicKey) throws Exception {
		Signature stool = Signature.getInstance("MD5WithRSA");
		stool.initVerify(getPublicKey(publicKey));
		stool.update(content.getBytes(GlobalStatic.UTF8));
		//验证签名是否正常
		return stool.verify(Base64.getDecoder().decode(signature));
	}

	public static String encText(String data, String publicKey) throws Exception {
		//获取一个加密算法为RSA的加解密器对象cipher。
		Cipher cipher = Cipher.getInstance("RSA");
		//设置为加密模式,并将公钥给cipher。
		cipher.init(Cipher.ENCRYPT_MODE, RsaUtils.getPublicKey(publicKey));
		//获得密文
		byte[] bytes = data.getBytes();
		if (bytes.length > 117) {
			byte[][] dataBytes = splitBytes(bytes);
			byte[][] result = new byte[dataBytes.length][];
			for (int i = 0; i < dataBytes.length; i++) {
				result[i] = cipher.doFinal(dataBytes[i]);
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < result.length; i++) {
				sb.append(Base64.getEncoder().encodeToString(result[i]));
				if (result.length - 1 != i) {
					sb.append(",");
				}
			}
			return sb.toString();
		} else {
			byte[] secret = cipher.doFinal(bytes);
			//进行Base64编码
			return Base64.getEncoder().encodeToString(secret);
		}
	}

	public static String decText(String secret, String privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		//传递私钥，设置为解密模式。
		cipher.init(Cipher.DECRYPT_MODE, RsaUtils.getPrivateKey(privateKey));
		//解密器解密由Base64解码后的密文,获得明文字节数组
		if (secret.contains(",")) {
			String[] strings = secret.split(",");
			byte[][] bytes = new byte[strings.length][];
			int length = 0;
			for (int i = 0; i < strings.length; i++) {
				bytes[i] = cipher.doFinal(Base64.getDecoder().decode(strings[i]));
				length += bytes[i].length;
			}
			byte[] byteResult = new byte[length];
			int index = 0;
			for (byte[] aByte : bytes) {
				for (byte b : aByte) {
					byteResult[index] = b;
					index++;
				}
			}
			return new String(byteResult);
		} else {
			byte[] b = cipher.doFinal(Base64.getDecoder().decode(secret));
			//转换成字符串
			return new String(b);
		}
	}


	private static byte[][] splitBytes(byte[] bytes) {
		double splitLength = 100.0;
		int arrayLength = (int) Math.ceil(bytes.length / splitLength);
		byte[][] result = new byte[arrayLength][];
		int from, to;
		for (int i = 0; i < arrayLength; i++) {

			from = (int) (i * splitLength);
			to = (int) (from + splitLength);
			if (to > bytes.length) {
				to = bytes.length;
			}
			result[i] = Arrays.copyOfRange(bytes, from, to);
		}
		return result;
	}

}