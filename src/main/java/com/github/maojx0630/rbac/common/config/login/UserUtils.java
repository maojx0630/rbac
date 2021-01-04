package com.github.maojx0630.rbac.common.config.login;

import cn.hutool.core.util.StrUtil;
import com.github.maojx0630.rbac.common.config.global.GlobalStatic;
import com.github.maojx0630.rbac.common.config.login.login.LoginConfuse;
import com.github.maojx0630.rbac.common.config.login.user.ConfuseInfo;
import com.github.maojx0630.rbac.common.config.login.user.PublicAndUnique;
import com.github.maojx0630.rbac.common.config.login.user.TokenInfo;
import com.github.maojx0630.rbac.common.config.login.user.UserLoginDeviceInfo;
import com.github.maojx0630.rbac.common.config.redis.RedisHeaderEnum;
import com.github.maojx0630.rbac.common.config.redis.RedisUtils;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.config.response.exception.StateException;
import com.github.maojx0630.rbac.common.config.response.log.ThreadLocalRemove;
import com.github.maojx0630.rbac.common.config.response.result.ResponseResultState;
import com.github.maojx0630.rbac.common.utils.RequestUtils;
import com.github.maojx0630.rbac.common.utils.SpringUtils;
import com.github.maojx0630.rbac.common.utils.rsa.RsaEntity;
import com.github.maojx0630.rbac.common.utils.rsa.RsaUtils;
import com.github.maojx0630.rbac.sys.dto.LoginUserInfo;
import com.github.maojx0630.rbac.sys.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户工具类
 * <br/>
 * @author MaoJiaXing
 * @date 2020-04-21 13:38 
 */
@Slf4j
@Component
public class UserUtils implements ThreadLocalRemove {

	@Autowired
	private RedisUtils redisUtils;

	private static final ThreadLocal<Long> USER_INFO_THREAD_LOCAL = new ThreadLocal<>();

	private static final ThreadLocal<String> USER_UUID_THREAD_LOCAL = new ThreadLocal<>();

	//获取登陆用户，如果未获取到则抛出该用户未登陆
	public static LoginUserInfo getUser() {
		return getOptUser().orElseThrow(StateEnum.user_not_login);
	}

	//获取登陆用户，可能为空
	public static Optional<LoginUserInfo> getOptUser() {
		Long id=USER_INFO_THREAD_LOCAL.get();
		if(Objects.isNull(id)){
			return Optional.empty();
		}
		RedisUtils redis = SpringUtils.getBean(RedisUtils.class);
		Optional<LoginUserInfo> loginUserInfo = redis.get(getUserHead(id), LoginUserInfo.class);
		LoginUserInfo info= loginUserInfo.orElseGet(()->{
			LoginUserInfo userInfo = SpringUtils.getBean(SysUserService.class).getLoginUserInfo(id);
			redis.set(getUserHead(id), userInfo);
			return userInfo;
		}) ;
		return Optional.of(info);
	}

	/**
	 * 登出当前用户 当前设备
	 */
	public static boolean logout(){
		String loginHead = getLoginHead(USER_INFO_THREAD_LOCAL.get());
		String uuid = USER_UUID_THREAD_LOCAL.get();
		if(StrUtil.isBlank(loginHead)||StrUtil.isBlank(uuid)){
			return false;
		}
		return SpringUtils.getBean(RedisUtils.class).hashDel(loginHead, uuid) == 1L;
	}

	/**
	 * 登录用户接口 不会让之前用户过期 自动获取当前访问ip
	 * @param id 用户id
	 * @param device 用户设备名称
	 * @param expire 过期时间
	 * @return 登录信息
	 */
	public PublicAndUnique login(Long id, String device, long expire){
		return login(id,device,RequestUtils.getIp(),expire);
	}

	/**
	 * 登陆用户接口 不会自动让之前用户过期
	 * <br/>
	 * @param id 用户id
	 * @param device 登陆设备名称
	 * @param ip 登陆ip 不传自动获取当前线程的访问ip
	 * @param expire 过期时间 单位毫秒  访问会刷新剩余过期时间
	 * @return com.zfei.rehabilitation.common.config.login.user.PublicAndUnique
	 * @author MaoJiaXing
	 * @date 2020-12-17 15:11
	 */
	public PublicAndUnique login(Long id, String device, String ip,long expire) {
		try {
			//创建rsa
			RsaEntity rsa = RsaUtils.createKeyPair();
			//获取混淆登陆信息
			ConfuseInfo confusion = LoginConfuse.confusion(id);
			//创建tokenInfo对象 后续会存到
			TokenInfo tokenInfo = new TokenInfo(id, confusion.getUuid(), rsa, device,ip, expire);
			clear(id);
			redisUtils.hashPut(getLoginHead(id), confusion.getUuid(), tokenInfo);
			return new PublicAndUnique(rsa.getPublicKey(), confusion.getHex());
		} catch (Exception e) {
			if (e instanceof ResponseResultState) {
				throw new StateException((ResponseResultState) e);
			}
			e.printStackTrace();
			throw StateEnum.error.create();
		}
	}

	/**
	 * 验证用户登陆
	 * <br/>
	 * @return boolean
	 * @author MaoJiaXing
	 * @date 2020-12-17 15:11
	 */
	public boolean validation() {
		HttpServletRequest request = RequestUtils.getRequest();
		String token = request.getHeader(GlobalStatic.AUTHENTICATION_HEAD);
		String unique = request.getHeader(GlobalStatic.UNIQUE_HEAD);
		if (StrUtil.isBlank(token) || StrUtil.isBlank(unique)) {
			throw StateEnum.token_check_failure.create();
		}
		if ("1".equals(redisUtils.repeatToken(token))) {
			throw StateEnum.token_repeat.create();
		}
		ConfuseInfo info = LoginConfuse.decryption(unique);
		String loginHead = getLoginHead(info.getId());
		TokenInfo tokenInfo = redisUtils.hashGet(loginHead, info.getUuid(), TokenInfo.class).
				orElseThrow(StateEnum.token_check_failure);
		//校验token是否过期
		if (checkEx(tokenInfo)) {
			redisUtils.hashDel(loginHead, tokenInfo.getUuid());
			throw StateEnum.token_check_failure.create();
		}
		long time;
		String timeStr;
		try {
			timeStr = RsaUtils.decryptWithRSA(token, tokenInfo.getPrivateKey());
			time = Long.parseLong(timeStr);
		} catch (Exception e) {
			log.debug("token解密失败");
			throw StateEnum.token_check_failure.create();
		}
		if ((System.currentTimeMillis() - time) > 60000) {
			throw StateEnum.token_request_timeout.create();
		}
		//重置最后访问时间
		tokenInfo.setLastDate(System.currentTimeMillis());
		redisUtils.hashPut(getLoginHead(tokenInfo.getId()), tokenInfo.getUuid(), tokenInfo);
		USER_INFO_THREAD_LOCAL.set(tokenInfo.getId());
		USER_UUID_THREAD_LOCAL.set(tokenInfo.getUuid());
		return true;
	}

	/**
	 * 踢出该用户所有的登陆设备
	 * <br/>
	 * @param id 用户id
	 * @author MaoJiaXing
	 * @date 2020-12-11 16:56
	 */
	public void kickedOutUser(Long id) {
		redisUtils.del(getLoginHead(id));
	}

	/**
	 * 踢出当前登陆用户指定设备登陆
	 * <br/>
	 * @param uuid 登陆设备uuid
	 * @return boolean
	 * @author MaoJiaXing
	 * @date 2020-12-14 11:11
	 */
	public boolean kickedOutDevice(String uuid) {
		return kickedOutDevice(getUser().getId(), uuid);
	}

	/**
	 * 踢出指定用户指定设备id
	 * <br/>
	 * @param userId 用户id
	 * @param uuid 登陆设备id
	 * @return boolean
	 * @author MaoJiaXing
	 * @date 2020-12-14 11:12
	 */
	public boolean kickedOutDevice(Long userId, String uuid) {
		String loginHead = getLoginHead(userId);
		if (redisUtils.hasKey(loginHead) && redisUtils.hashOperationsHasKey(loginHead, uuid)) {
			redisUtils.hashDel(loginHead, uuid);
			return true;
		}
		return false;
	}

	/**
	 * 获取当前用户的所有登陆信息
	 * <br/>
	 * @return java.util.List<com.zfei.rehabilitation.common.config.login.user.UserLoginDeviceInfo>
	 * @author MaoJiaXing
	 * @date 2020-12-14 09:52
	 */
	public List<UserLoginDeviceInfo> getUserDevice(){
		return getUserDevice(getUser().getId());
	}

	/**
	 * 获取用户的所有登陆信息
	 * <br/>
	 * @param userId 用户id
	 * @return java.util.List<com.zfei.rehabilitation.common.config.login.user.UserLoginDeviceInfo>
	 * @author MaoJiaXing
	 * @date 2020-12-14 09:52
	 */
	public List<UserLoginDeviceInfo> getUserDevice(Long userId) {
		//先清除已经过期的登陆信息
		clear(userId);
		Optional<List<TokenInfo>> array = redisUtils.getArray(getLoginHead(userId), TokenInfo.class);
		if ((!array.isPresent()) || array.get().isEmpty()) {
			return Collections.emptyList();
		}
		List<TokenInfo> list = array.get();
		return SpringUtils.copy(list, UserLoginDeviceInfo.class);
	}

	/**
	 * 清除指定用户的缓存信息
	 * @param id 用户id
	 */
	public void clearUser(Long id){
		redisUtils.del(getUserHead(id));
	}

	public Long clearAllUser(){
		Set<String> scan = redisUtils.scan(RedisHeaderEnum.USER_INFO_HEAD_.name());
		if(scan.isEmpty()){
			return 0L;
		}else{
			return redisUtils.del(scan);
		}
	}

	/**
	 * 清理用户已经过期的验证信息
	 * <br/>
	 * @author MaoJiaXing
	 * @date 2020-12-11 16:55
	 */
	private void clear(Long id) {
		String loginHead = getLoginHead(id);
		if (!redisUtils.hasKey(loginHead)) {
			return;
		}
		Optional<List<TokenInfo>> array = redisUtils.hashGetArray(loginHead, TokenInfo.class);
		if (array.isPresent()) {
			List<TokenInfo> list = array.get();
			if (!list.isEmpty()) {
				Set<String> set = new HashSet<>();
				for (TokenInfo info : list) {
					if (checkEx(info)) {
						set.add(info.getUuid());
					}
				}
				if (set.size() == list.size()) {
					redisUtils.del(loginHead);
				} else if (set.isEmpty()) {
					return;
				} else {
					redisUtils.hashDel(loginHead, set.toArray(new String[0]));
				}
				return;
			}
		}
		redisUtils.del(loginHead);
	}

	/**
	 * 验证token是否过期
	 * <br/>
	 * @param info token信息 不能为空
	 * @return boolean true 过期  false 没过期
	 * @author MaoJiaXing
	 * @date 2020-12-11 16:38
	 */
	private boolean checkEx(TokenInfo info) {
		return System.currentTimeMillis() > info.getLastDate() + info.getExpire();

	}

	//移除用户登陆信息，防止内存泄漏
	@Override
	public void remove() {
		USER_INFO_THREAD_LOCAL.remove();
		USER_UUID_THREAD_LOCAL.remove();
	}

	private static String getUserHead(Long id) {
		return RedisHeaderEnum.USER_INFO_HEAD_.name() + id;
	}

	private static String getLoginHead(Long id) {
		return RedisHeaderEnum.LOGIN_HEAD_.name() + id;
	}

}
