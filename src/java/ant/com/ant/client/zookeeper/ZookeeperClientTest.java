package com.ant.client.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * @ClassName ZookeeperClientTest
 * @Description ZookeeperClientTest
 * @Author Ant
 * @Date 2019-03-06 20:14
 * @Version 1.0
 **/
public class ZookeeperClientTest {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper client = new ZooKeeper("localhost:2181", 70000, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("链接时候"+event);
            }
        });

        Stat stat = new Stat();
       client.getData("/testW", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if(Event.EventType.NodeDataChanged.equals(event.getType())){
                    System.out.println("数据发生了改变");
                }
            }
        }, stat);

//        String string = new String(client.getData("/data", true, stat));
        // 创建节点
//        client.create("/data", "1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
//
//        client.getData("/data", false, new AsyncCallback.DataCallback() {
//            @Override
//            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
//                System.out.println("AsyncCallback");
//            }
//        }, stat);

        System.in.read();
    }
}
