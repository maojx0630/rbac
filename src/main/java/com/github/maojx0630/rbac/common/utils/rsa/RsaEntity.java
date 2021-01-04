package com.github.maojx0630.rbac.common.utils.rsa;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * 保存rsa公私钥字符串
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-20 10:23 
 */
public class RsaEntity {
	private String publicKey;
	private String privateKey;

	public RsaEntity(PublicKey publicKey, PrivateKey privateKey) {
		super();
		this.publicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
		this.privateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}
}
