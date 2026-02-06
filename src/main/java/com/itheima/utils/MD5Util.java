package com.itheima.utils;
import java.util.Base64;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
//import sun.misc.BASE64Encoder;
/**
 * 密码加密工具类
 * @author 黑马程序员
 * @Company http://www.itheima.com
 */
public class MD5Util {
    /**
     * 密码加密
     * @param password
     * @return
     * @throws Exception
     */
    public static String  md5(String password){
        try {
            //1.创建加密对象
            MessageDigest md5 = MessageDigest.getInstance("md5");
            //2.加密密码
            byte[] by = md5.digest(password.getBytes(StandardCharsets.UTF_8));
            //3.创建编码对象
//            BASE64Encoder encoder = new BASE64Encoder();
            //4.对结果编码
//            return encoder.encode(by);
            return Base64.getEncoder().encodeToString(by);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    // 测试加密效果（可选）
    public static void main(String[] args) {
        String original = "77";
        String encrypted = md5(original);
        System.out.println("原始密码：" + original);
        System.out.println("加密后：" + encrypted);
        // 输出示例：KmFGVXlSdmltaHZUQ2d2Zw==（正确的Base64编码结果）
    }
}
