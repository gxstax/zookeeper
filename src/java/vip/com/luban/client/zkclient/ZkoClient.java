package com.luban.client.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.IOException;
import java.util.List;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class ZkoClient {

    public static void main(String[] args) throws IOException {
        ZkClient zk = new ZkClient("localhost:2181",10000, 10000, new SerializableSerializer());

        // 持久节点
        zk.createPersistent("/zkclient", "zhouyu".getBytes());

        // 临时节点
//        zk.createEphemeral("/zkclient_EPHEMERAL", "zhouyu".getBytes());


        // 持久顺序节点
//        zk.createPersistentSequential("/zkclient_", "zhouyu".getBytes());
//        zk.createPersistentSequential("/zkclient_", "zhouyu".getBytes());


        // 临时顺序节点
//        String nodeName = zk.createEphemeralSequential("/zkclient_EPHEMERAL_", "zhouyu".getBytes());
//        zk.createEphemeralSequential("/zkclient_EPHEMERAL_", "zhouyu".getBytes());
//
//        System.out.println(nodeName);
//
//        System.out.println(zk.exists("/lubanwatch"));

        zk.subscribeDataChanges("/zkclient", new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("数据被改了");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("数据被删了");
            }
        });

//        List<String> list = zk.getChildren("/");
//        System.out.println(list);

        System.in.read();
    }
}
