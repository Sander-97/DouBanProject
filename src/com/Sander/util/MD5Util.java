package com.Sander.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName MD5Util
 * @Direction: MD5加密---单向加密 返回大写的MD5 , 转化为32位的字符串
 * @Author: Sander
 * @Date 2021/10/11 10:28
 * @Version 1.0
 **/
public class MD5Util {
    /**
     * 字符数组---储存返回的MD5处理结果的32位中的16进制数字
     */
    private static final String HEX_DIGITS[] = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

    /**
     *  MD5加密
     * @param origin
     * @param charsetName 要加密的密码字符串
     * @return 加密后的32位字符串
     */
    public static String MD5Encode(String origin , String charsetName){
        String resultString = null;

        try {
            resultString = new String(origin);
            //获取messageDigest实例，并指定加密算法类型--生成一个MD5加密计算概要
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //字符串为空不存在或者字符串内容为空
            if (null == charsetName || "".equals(charsetName)){
                resultString = byteArrayToHexString(messageDigest.digest(resultString.getBytes()));
            }else {
                //digest最后确定返回md5 hash值，返回值为8位字符串。因为md5 hash值是实际上的8位字符
                //md5的计算结果是一个128位的长整数，用字节表示为16给字节 --128 = 16 * 8
                //一个byte是八位二进制bit，也就是2位十六进制字符（2的8次方等于16的2次方==256）
                resultString = byteArrayToHexString(messageDigest.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 主要是遍历8个byte，转化为16进制的字符，也就是0-F
     * @param b
     * @return 32位16进制的字符串
     */
    public static String byteArrayToHexString(byte b[]){
        StringBuffer resultSb = new StringBuffer();

        for (int i = 0; i < b.length; i++) {
            //调用处理单个byte的方法，拼接成一个字符串
            resultSb.append(byteToHexString(b[i]));
        }
        //把StringBuffer转换成String
        return resultSb.toString();
    }

    /**
     * 针对单个的byte，一个byte（-128~127）--16拆分为d1和d2（两个16以内的数字）充当数组的下标。
     * @param b
     * @return 数组值
     */
    public static String byteToHexString(byte b){
        int n = b;
        if (n<0){
            n+=256;
        }
        int d1 = n/16;

        int d2 = n%16;

        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }



}
