package com.ant.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @ClassName CuratorClient
 * @Description CuratorClient
 * @Author Ant
 * @Date 2019-03-06 21:27
 * @Version 1.0
 **/
public class CuratorClient {

    static String PATH =  "/curator/lock";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new RetryNTimes(3, 10000));
        client.start();

//        client.create().withMode(CreateMode.EPHEMERAL).forPath(PATH, "2".getBytes());

//        NodeCache nodeCache = new NodeCache(client, path);
//        nodeCache.start(false);
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("nodeChanged");
//            }
//        });

        client.getData().usingWatcher(new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("使用了watch");
            }
        }).forPath(PATH);

        System.in.read();
    }
}
