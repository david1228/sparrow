package com.zoo.sparrow.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * generate md5 sign util
 *
 * @author liyunlong
 */
public class MD5Util {

    private final static String[] strDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f"};

    /**
     * Transform byte to hexadecimal string
     *
     * @param b
     * @return
     */
    private static String transformByteToHexadecimal(byte b) {
        int tmp = b;
        if (b < 0) {
            tmp += 256;
        }
        int index1 = tmp / 16;
        int index2 = tmp % 16;
        return strDigits[index1] + strDigits[index2];
    }

    /**
     * Transform bytes to string
     *
     * @param bytes
     * @return
     */
    private static String transformByteToString(byte[] bytes) {
        if ((bytes == null) || (bytes.length == 0)) {
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sBuffer.append(transformByteToHexadecimal(bytes[i]));
        }
        return sBuffer.toString();
    }

    /**
     * Generate md5 encrypted string
     *
     * @param value
     * @return
     */
    public static String md5(String value) {
        if (value == null) {
            return null;
        }
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = transformByteToString(md.digest(value.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.md5("!@s20190127"));
    }
}
