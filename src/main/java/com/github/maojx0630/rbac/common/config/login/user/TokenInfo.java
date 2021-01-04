package com.github.maojx0630.rbac.common.config.login.user;

import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.utils.RequestUtils;
import com.github.maojx0630.rbac.common.utils.rsa.RsaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * redis存储的登陆用户验证
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-21 14:33 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfo {

	//用户id
	private Long id;

	private String uuid;

	private String publicKey;

	private String privateKey;

	//登陆时间
	private long loginDate;

	//登陆ip
	private String loginIp;

	//登陆地址
	private String loginAddr;

	//最后一次访问时间
	private long lastDate;

	//登陆设备名称
	private String device;

	//过期时间
	private long expire;

	public TokenInfo(Long id, String uuid, RsaEntity rsaEntity,
	                 String device,String ip, long expire) {
		this.id = id;
		this.uuid = uuid;
		this.publicKey = rsaEntity.getPublicKey();
		this.privateKey = rsaEntity.getPrivateKey();
		this.loginDate =  System.currentTimeMillis();
		this.loginIp = ip;
		if(StrUtil.isNotBlank(ip)){
			this.loginAddr = RequestUtils.getIpAddress(ip);
		}
		this.lastDate = System.currentTimeMillis();
		this.device = device;
		this.expire = expire;
	}
}
