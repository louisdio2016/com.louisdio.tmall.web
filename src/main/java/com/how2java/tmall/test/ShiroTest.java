package com.how2java.tmall.test;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

public class ShiroTest {
    @Test
    public void testSHA256(){
        //4f9eb48d371e25b05d5df80eebb343c6bfb067d274301db24dd26d26e8aeb6ab
        //4739ee3bd29e4f415da8ba9298a087e0fdc9c61378420ba8fbbab298bd74c4df
        String s = new Sha256Hash("123", "adam").toString();
        System.out.println(s);
        System.out.println(s.length());
    }
}
