package com.how2java.tmall.util;

import org.apache.shiro.crypto.hash.Sha256Hash;

/**
 * sha256散列算法加密
 */
public class SHAUtil {
    public static String sha(String str,String salt){
        return new Sha256Hash(str, salt).toString();
    }
}
