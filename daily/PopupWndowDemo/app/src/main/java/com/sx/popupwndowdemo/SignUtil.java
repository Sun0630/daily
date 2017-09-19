package com.sx.popupwndowdemo;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * API 参数签名算法
 * Created By zhangtengfei
 * Date:2017/9/14
 * Time:11:19
 */
public class SignUtil {

    private static Charset CHARSET = Charset.forName("utf-8");

    public static String sign(String secretKey, Map<String, String> params) throws Exception {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            if (i == 0) {
                prestr += keys.get(i) + "=" + params.get(keys.get(i));
            } else {
                prestr += "&" + keys.get(i) + "=" + params.get(keys.get(i));
            }
        }
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(secretKey.getBytes("UTF-8"));
        byte[] result = md5.digest(prestr.getBytes("UTF-8"));
//        return Hex.encodeHexString(result);
        return byteArray2HexStr(result);
    }

    public static boolean verify(String sign, String secretKey, Map<String, String> params) throws Exception {
        if (null == sign) {
            return false;
        } else {
            return sign.equals(sign(secretKey, params));
        }
    }

    /**
     * 处理字节数组得到MD5密码的方法
     */
    private static String byteArray2HexStr(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bs) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }

    /**
     * 字节标准移位转十六进制方法
     */
    private static String byte2HexStr(byte b) {
        String hexStr = null;
        int n = b;
        if (n < 0) {
            n = b & 0x7F + 128;
        }
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
        return hexStr;
    }
}
