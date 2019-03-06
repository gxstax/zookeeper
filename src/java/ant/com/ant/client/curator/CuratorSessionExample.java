package com.ant.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;

/**
 * @ClassName CuratorSessionExample
 * @Description CuratorSessionExample
 * @Author Ant
 * @Date 2019-03-06 22:52
 * @Version 1.0
 **/
public class CuratorSessionExample {
    public static void main(String[] args) {
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", 10000, 10000,
                new RetryNTimes(3, 10000));

        client.start();
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
                if(connectionState == ConnectionState.LOST) {
                    try {
                        if(client.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                            // 网络断开重新连接后，需要重新把之前的动作再做一遍
                            doTask();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //
        doTask();
    }

    private static void doTask() {
    }
}
