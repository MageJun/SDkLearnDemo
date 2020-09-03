package com.learn.plugin.strproguard
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

public class EncDecUtils {

    public static String md5(String paramString) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            byte[] encryptStr = messageDigest.digest(paramString.getBytes());
            return byte2hex(encryptStr).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String byte2hex(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer(
                paramArrayOfByte.length * 2);
        int i = 0;
        while (i < paramArrayOfByte.length) {
            String str = Integer.toHexString(paramArrayOfByte[i] & 0xFF);
            if (str.length() == 1) {
                localStringBuffer.append("0");
            }
            localStringBuffer.append(str);
            i += 1;
        }
        return localStringBuffer.toString().toUpperCase();
    }
}