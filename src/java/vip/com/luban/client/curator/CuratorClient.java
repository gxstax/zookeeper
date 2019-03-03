package com.luban.client.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

/**
 * @ClassName CuratorClient
 * @Description CuratorClient
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class CuratorClient {
    public static void main(String[] args) throws Exception {

        // 重试机制
        // 1.RetryUntilElapsed(int maxElapsedTimeMs, int sleepMsBetweenRetries)：以sleepMsBetweenRetries的间隔重连,直到超过maxElapsedTimeMs的时间设置
        // 2.RetryNTimes(int n, int sleepMsBetweenRetries) :指定重连次数
        // 3.RetryOneTime(int sleepMsBetweenRetry):重连一次
        // 4.ExponentialBackoffRetry:时间间隔 = baseSleepTimeMs * Math.max(1, random.nextInt(1 << (retryCount + 1)))

        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", new RetryNTimes(3, 1000));
        client
//                .usingNamespace("/testNamespace") // 如果有命名空间，那么后续的forPath都会加上namespace
                .start();


        String path = "/curator";
//        String result = client.create().forPath("/curator");
//        System.out.println(result);

//        client.setData().forPath(path, "3".getBytes());
//        client.create().withMode(CreateMode.EPHEMERAL).forPath(path);

//        client.getData().usingWatcher(new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                System.out.println("用的是watcher");
//            }
//        }).forPath(path);

        // ZooKeeper原生支持通过注册Watcher来进行事件监听，但是其使用并不是特别方便，需要开发人员自己反复注册Watcher，比较繁琐。
        // Curator引入了Cache来实现对ZooKeeper服务端事件的监听。Cache是Curator中对事件监听的包装，其对事件的监听其实可以近似看作是一个本地缓存视图和远程ZooKeeper视图的对比过程。
        // 同时Curator能够自动为开发人员处理反复注册监听，从而大大简化了原生API开发的繁琐过程。
        // Cache分为两类监听类型：节点监听和子节点监听。

//        NodeCache nodeCache = new NodeCache(client, path, false);
//        nodeCache.start(true); // 该方法有个boolean类型的参数，默认是false，如果设置为true，那么NodeCache在第一次启动的时候就会立刻从ZooKeeper上读取对应节点的数据内容，并保存在Cache中.
//        // NodeCache不仅可以用于监听数据节点的内容变更，也能监听指定节点是否存在。如果原本节点不存在，那么Cache就会在节点被创建后触发NodeCacheListener。但是，如果该数据节点被删除，那么Curator就无法触发NodeCacheListener了。
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("数据改变了");
//            }
//        });


//        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, false); // cacheData代表如果配置为true，那么客户端在接收到节点列表变更的同时，也能够获取到节点的数据内容；如果配置为false，则无法获取到节点的数据内容
//        pathChildrenCache.start();
//        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
//                PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();
//                switch (type) {
//                    case CHILD_ADDED:
//                        System.out.println("CHILD_ADDED");
//                        break;
//                    case CHILD_UPDATED:
//                        System.out.println("CHILD_UPDATED");
//                        break;
//                    case CHILD_REMOVED:
//                        System.out.println("CHILD_REMOVED");
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });


        System.in.read();

    }
}
