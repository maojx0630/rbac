package com.github.maojx0630.rbac.common.utils.file;


import com.github.maojx0630.rbac.common.config.response.exception.StateEnum;
import com.github.maojx0630.rbac.common.utils.UrlSafeBase64;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 计算文件内容或者二进制数据的etag, etag算法是七牛用来标志数据唯一性的算法。
 * 文档：<a href="https://github.com/qiniu/qetag">etag算法</a>
 */
public final class Etag {

    private static final int BLOCK_SIZE = 4 * 1024 * 1024;

    private Etag() {
    }

    /**
     * 计算文件内容的etag
     *
     * @param file 文件对象
     * @return 文件内容的etag
     */
    public static String tag(File file) {
        try (FileInputStream fi = new FileInputStream(file)) {
            return stream(fi, file.length());
        } catch (Exception e) {
            throw StateEnum.file_tag_error.create();
        }

    }

    /**
     * 计算文件内容的etag
     *
     * @param file 文件对象
     * @return 文件内容的etag
     */
    public static String tag(MultipartFile file) {
        try (InputStream fi = file.getInputStream()) {
            return stream(fi, file.getSize());
        } catch (Exception e) {
            throw StateEnum.file_tag_error.create();
        }

    }

    /**
     * 计算输入流的etag，如果计算完毕不需要这个InputStream对象，请自行关闭流
     *
     * @param in  数据输入流
     * @param len 数据流长度
     * @return 数据流的etag值
     * @throws IOException 文件读取异常
     */
    private static String stream(InputStream in, long len) throws IOException {
        if (len == 0) {
            return "Fto5o-5ea0sNMlW_75VgGJCv2AcJ";
        }
        byte[] buffer = new byte[64 * 1024];
        byte[][] blocks = new byte[(int) ((len + BLOCK_SIZE - 1) / BLOCK_SIZE)][];
        for (int i = 0; i < blocks.length; i++) {
            long left = len - (long) BLOCK_SIZE * i;
            long read = left > BLOCK_SIZE ? BLOCK_SIZE : left;
            blocks[i] = oneBlock(buffer, in, (int) read);
        }
        return resultEncode(blocks);
    }

    /**
     * 单块计算hash
     *
     * @param buffer 数据缓冲区
     * @param in     输入数据
     * @param len    输入数据长度
     * @return 计算结果
     * @throws IOException 读取出错
     */
    private static byte[] oneBlock(byte[] buffer, InputStream in, int len) throws IOException {
        MessageDigest sha1;
        try {
            sha1 = MessageDigest.getInstance("sha-1");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
        int buffSize = buffer.length;
        while (len != 0) {
            int next = Math.min(buffSize, len);
            //noinspection ResultOfMethodCallIgnored
            in.read(buffer, 0, next);
            sha1.update(buffer, 0, next);
            len -= next;
        }

        return sha1.digest();
    }

    /**
     * 合并结果
     *
     * @param sha1s 每块计算结果的列表
     * @return 最终的结果
     */
    private static String resultEncode(byte[][] sha1s) {
        byte head = 0x16;
        byte[] finalHash = sha1s[0];
        int len = finalHash.length;
        byte[] ret = new byte[len + 1];
        if (sha1s.length != 1) {
            head = (byte) 0x96;
            MessageDigest sha1;
            try {
                sha1 = MessageDigest.getInstance("sha-1");
            } catch (NoSuchAlgorithmException e) {
                throw new AssertionError(e);
            }
            for (byte[] s : sha1s) {
                sha1.update(s);
            }
            finalHash = sha1.digest();
        }
        ret[0] = head;
        System.arraycopy(finalHash, 0, ret, 1, len);
        return UrlSafeBase64.encodeToString(ret);
    }

}
