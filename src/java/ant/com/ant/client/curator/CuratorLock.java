package com.ant.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName CuratorClient
 * @Description CuratorClient
 * @Author Ant
 * @Date 2019-03-06 21:27
 * @Version 1.0
 **/
public class CuratorLock {

    static String PATH =  "/curator/lock";
    static String group = "ant-1";

    public static void main(String[] args) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181",
                new RetryNTimes(3, 10000));
        client.start();

//        client.create().withMode(CreateMode.EPHEMERAL).forPath(PATH, "2".getBytes());

        //创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, PATH + "/" + group );

        long start = System.currentTimeMillis();
        try {
            mutex.acquire(1 * 30, TimeUnit.SECONDS);

            Thread.sleep(100000);
            for (int i = 0; i < 10; i++) {
                InterProcessMutex mutex2 = new InterProcessMutex(client, PATH + "/" + group );
                System.out.println(mutex2.acquire(1 * 30, TimeUnit.SECONDS));
            }
            long end = System.currentTimeMillis();

            System.out.println((end-start)/1000 + " s");
            System.out.println((end-start)/(60 * 1000) + " min");
            // 获得了锁, 进行业务流程
            System.out.println("Enter mutex");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 完成业务流程, 释放锁
            mutex.release();
            client.getZookeeperClient().getZooKeeper().delete(PATH + "/" + group, -1);
            //关闭客户端
            client.close();
        }

//        System.in.read();
    }
}
