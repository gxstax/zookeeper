package com.ant.client.zkclient;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName ZkclientTest
 * @Description ZkclientTest
 * @Author Ant
 * @Date 2019-03-06 20:47
 * @Version 1.0
 **/
public class ZkclientTest {
    public static void main(String[] args) throws IOException {
        ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000, new SerializableSerializer());

//        zkClient.createPersistent("/data", "1".getBytes());

        zkClient.subscribeDataChanges("/data", new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("数据被改了");
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });

        System.in.read();
    }
}
