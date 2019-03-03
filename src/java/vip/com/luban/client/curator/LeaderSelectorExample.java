package com.luban.client.curator;

import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.RetryNTimes;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LeaderSelectorExample
 * @Description LeaderSelectorExample
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class LeaderSelectorExample {
    // 这种方式用的是锁

    public static void main(String[] args) throws IOException {
        List<CuratorFramework> clients = Lists.newArrayList();
        List<LeaderSelector> leaderSelectors = Lists.newArrayList();

        for (int i=0; i<10; i++) {
            CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(3, 3000));
            client.start();
            clients.add(client);

            LeaderSelector leaderSelector = new LeaderSelector(client, "/LeaderSelector", new LeaderSelectorListener() {
                @Override
                public void takeLeadership(CuratorFramework client) throws Exception {
                    // 当上Leader了就会进入这个方法
                    System.out.println("当前Leader是"+client);

                    TimeUnit.SECONDS.sleep(5);
                }

                @Override
                public void stateChanged(CuratorFramework client, ConnectionState newState) {

                }
            });
            leaderSelector.start();
            leaderSelectors.add(leaderSelector);
        }

        System.in.read();

        for (CuratorFramework client: clients) {
            client.close();
        }
        for (LeaderSelector selector: leaderSelectors) {
            selector.close();
        }
    }
}
