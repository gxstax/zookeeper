package com.luban.client.zookeeper;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

/**
 * @ClassName Test
 * @Description Test
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(DigestAuthenticationProvider.generateDigest("super:admin"));
    }
}
