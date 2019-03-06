package com.ant.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;

/**
 * @ClassName ZkclientTest
 * @Description ZkclientTest
 * @Author Ant
 * @Date 2019-03-06 20:47
 * @Version 1.0
 **/
public class ZkclientWatchTest {
    public static void main(String[] args) throws IOException {
        ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000, new SerializableSerializer());
        zkClient.writeData("/data", "7");
    }
}
