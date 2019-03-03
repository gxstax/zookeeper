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
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
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
