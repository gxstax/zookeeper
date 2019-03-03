package com.luban.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LeaderLatchExample
 * @Description LeaderLatchExample
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class LeaderLatchExample {
    // 这种方式用的临时节点

    public static void main(String[] args) throws Exception {

        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderLatch> leaderLatches = Lists.newArrayList();

        for (int i=0; i<10; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(3, 300));
            clients.add(client);
            client.start();

            LeaderLatch leaderLatch = new LeaderLatch(client, "/LeaderLatch", "client#"+i);
            leaderLatches.add(leaderLatch);
            leaderLatch.start();

        }

        TimeUnit.SECONDS.sleep(5);

        for (LeaderLatch leaderLatch: leaderLatches) {
            if (leaderLatch.hasLeadership()) {
                System.out.println("当前Leader是"+leaderLatch.getId());
                break;
            }
        }

        System.in.read();

        for (CuratorFramework client: clients) {
            client.close();
        }

    }
}
