package com.github.maojx0630.rbac.common.config.login.login;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.github.maojx0630.rbac.common.config.login.user.ConfuseInfo;
import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;

/**
 * 登陆用户id混淆
 * <br/>
 *
 * @author MaoJiaXing
 * @date 2020-11-24 13:30
 */
public class LoginConfuse {

    private static final int LENGTH = 2;

    //混淆id信息
    public static ConfuseInfo confusion(Long id) {
        //获取两位随机字符串
        String s = RandomUtil.randomString(LENGTH);
        //生成uuid
        String uuid = IdUtil.fastSimpleUUID();
        //通过2位随机字符串计算出一个数字
        int length = reduction(s);
        //通过随机字符串得出数字随机生成一个字符串
        String aesConfusion = RandomUtil.randomString(length);
        //取前24位作为aes秘钥
        AES aes = SecureUtil.aes(aesConfusion.substring(0, 24).getBytes());
        //aes加密id  拼接uuid和随机字符串混淆
        String hex = aes.encryptHex(uuid + "_" + id + "_" + RandomUtil.randomString(5));
        //将两位随机字符串 aes秘钥随机字符串 加密后信息 拼接
        return new ConfuseInfo(id, uuid, s + aesConfusion + hex);
    }

    //按混淆过程逐步还原
    public static ConfuseInfo decryption(String hex) {
        try {
            String s = hex.substring(0, LENGTH);
            int reduction = reduction(s);
            String aesConfusion = hex.substring(LENGTH, reduction + LENGTH);
            String uniqueHex = hex.substring(reduction + LENGTH);
            AES aes = SecureUtil.aes(aesConfusion.substring(0, 24).getBytes());
            String unique = aes.decryptStr(uniqueHex);
            String uuid = unique.split("_")[0];
            String id = unique.split("_")[1];
            return new ConfuseInfo(Long.valueOf(id), uuid, hex);
        } catch (Exception e) {
            throw StateEnum.token_check_failure.create().setData(e.getMessage());
        }
    }

    private static int reduction(String s) {
        char[] chars = s.toCharArray();
        int i = 0;
        for (char c : chars) {
            i += c;
        }
        int gap = 0;
        int gapNum = i;
        while (true) {
            for (char c : (gapNum + "").toCharArray()) {
                int parseInt = Integer.parseInt(String.valueOf(c));
                gap += parseInt;
            }
            if (gap > 11) {
                gapNum = gap;
                gap = 0;
            } else if (gap <= 0) {
                gap = 7;
                break;
            } else {
                break;
            }
        }
        while (true) {
            if (i >= 24 && i <= 36) {
                return i;
            } else if (i < 24) {
                return 25;
            } else {
                i -= gap;
            }
        }

    }

}
