package cn.louyu.lylibrary.core.utils.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * MD5加密工具类（实现版）
 * MD5加密算法（Message-Digest Algorithm）是非对称加密算法
 * 优点：基于hash算法实现不可逆，压缩性，不容易修改，容易计算
 * 缺点：穷举法可以破解
 * @author louyu
 * @version 1.0
 */
public class MD5Util {

    /** 16进制形式的字符数组 */
    private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f' };


    public static String getMd5ByFile(File file,boolean uppercase) throws IOException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return uppercase?value.toUpperCase():value;
    }

    /**
     * 对字符串进行MD5加密(32位)
     * @param source 需要加密的原字符串
     * @param encoding 指定编码类型
     * @param uppercase 是否转为大写字符串
     * @return 加密后的字符串
     */
    public static String MD5Encode(String source, String encoding, boolean uppercase) {
        String result = null;
        try {
            result = source;
            // 获得MD5摘要对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            // 使用指定的字节数组更新摘要信息
            messageDigest.update(result.getBytes(encoding));
            // messageDigest.digest()获得16位长度
            result = byteArrayToHexString(messageDigest.digest());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return uppercase ? result.toUpperCase() : result;
    }
    /**
     * 对字符串进行MD5加密(16位)
     * @param source 需要加密的原字符串
     * @param encoding 指定编码类型
     * @param uppercase 是否转为大写字符串
     * @return 加密后的字符串
     */
    public static String MD5Encode16(String source, String encoding, boolean uppercase) {
        return MD5Encode(source,encoding,uppercase).substring(8,24);
    }

    /**
     * 转换字节数组为16进制字符串
     * @param bytes 字节数组
     * @return 转换后的字符串
     */
    private static String byteArrayToHexString(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for(byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = hexDigits[b >>> 4 & 0xf];
            buf[index++] = hexDigits[b & 0xf];
        }
        return new String(buf);
    }


}
