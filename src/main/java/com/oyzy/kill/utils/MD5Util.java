package com.oyzy.kill.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class MD5Util {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    //第一次加密前端到后台
    public static String inputPassToFromPass(String inputPass){
        String str =""+ salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    //第二次加密后台到数据库
    public static String fromPassToDBPass(String formPass,String salt){
        String str =""+ salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    //真正调用的方法
    public static String inputPassToDBPass(String inputPass,String salt){
        String fromPass = inputPassToFromPass(inputPass);
        String dbPass = fromPassToDBPass(fromPass,salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFromPass("Aa123321"));
        //46421ac0e1c78ef4b85a3dcb2bdc85f8
        System.out.println(fromPassToDBPass("46421ac0e1c78ef4b85a3dcb2bdc85f8",salt));
        System.out.println(inputPassToDBPass("123456",salt));
    }
}
