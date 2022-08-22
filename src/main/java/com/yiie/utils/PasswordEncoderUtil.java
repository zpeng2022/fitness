package com.yiie.utils;

import java.security.MessageDigest;

/**
 * Time：2020-1-2 8:58
 * Email： yiie315@163.com
 * Desc：密码加密工具类
 *
 * @author： yiie
 * @version：1.0.0
 */
public class PasswordEncoderUtil {
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
            "e", "f" };

    private final static String MD5 = "MD5";
    private final static String SHA = "SHA";

    private Object salt;
    private String algorithm;

    public PasswordEncoderUtil(Object salt) {
        this(salt, MD5);
    }

    public PasswordEncoderUtil(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    /**
     * 密码加密
     * @param rawPass
     * @return
     */
    public String encode(String rawPass) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            // 加密后的字符串
            result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
        } catch (Exception ex) {
        }
        return result;
    }

    /**
     * 密码匹配验证
     * @param encPass 密文
     * @param rawPass 明文
     * @return
     */
    public boolean matches(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encode(rawPass);

        return pass1.equals(pass2);
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password = "";
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b
     *            字节数组
     * @return 16进制字串
     */
    private String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将字节转换为16进制
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
