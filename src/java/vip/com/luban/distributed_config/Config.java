package com.luban.distributed_config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName Config
 * @Description Config
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class Config {

    private CuratorFramework client;
    private static final String DEFAULT_BASEPATH = "/distributed_config";
    private static final String DEFAULT_NAMES_PATH = "/distributed_config_names";
    private Map<String, String> cacheMap = new HashMap<String, String>();


    public Config(String connectStr) {
        client = CuratorFrameworkFactory.newClient(connectStr, new RetryNTimes(Integer.MAX_VALUE, 1000))
                .usingNamespace(DEFAULT_BASEPATH);
        client.start();
    }

    private void init() {
        _initWatch();
        _init();
    }

    /**
     * 配置中心启动时需要把所有的配置项拿到本地来
     */
    private void _init() {
        Set<String> names = _getNames();
        for (String name : names) {
            cacheMap.put(name, getDataFromZk(name));
        }
    }

    private String getDataFromZk(String name) {
        try {
            return new String(client.getData().forPath(name));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void _initWatch() {
        PathChildrenCache watcher = new PathChildrenCache(client, DEFAULT_BASEPATH, true);

        watcher.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data == null) {
                    System.out.println("No data in event, event = " + event);
                } else {
                    System.out.println("Receive event: "
                            + "type=[" + event.getType() + "]"
                            + ", path=[" + data.getPath() + "]"
                            + ", data=[" + new String(data.getData()) + "]"
                            + ", stat=[" + data.getStat() + "]");

                    if (event.getType() == PathChildrenCacheEvent.Type.CHILD_ADDED
                            || event.getType() == PathChildrenCacheEvent.Type.CHILD_UPDATED) {
                        String path = data.getPath();
                        if (path.startsWith(DEFAULT_BASEPATH)) {
                            String key = path.replace(DEFAULT_BASEPATH + "/", "");
                            String dataStr = new String(data.getData());
                            cacheMap.put(key, dataStr);
                        }
                    }
                }

            }
        });

        try {
            watcher.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void save(String key, String value) {

        // 存key, value
        cacheMap.put(key, value);
        _save(decKey(key), value.getBytes());

        // 存names
        Set<String> names = _getNames();
        names.add(decKey(key));
        _save(DEFAULT_NAMES_PATH, SerializableUtils.encode(names));

    }

    public String get(String key) {
        return cacheMap.get(key);
    }


    /**
     * 作为配置，一个基本的格式就是{key:value}
     *
     * @param key
     * @param value
     */
    private void _save(String key, byte[] value) {
        try {
            // 当前key是否已存在，如果存在则更新数据，不存在则更新
            Stat stat = client.checkExists().forPath(key);
            if (stat != null) {
                client.setData().forPath(key, value);
            } else {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private Set<String> _getNames() {
        try {
            Stat stat = client.checkExists().forPath(DEFAULT_NAMES_PATH);
            if (stat != null) {
                byte[] namesData = client.getData().forPath(DEFAULT_NAMES_PATH);

                if (namesData != null && namesData.length > 0) {
                    Set<String> names = (Set<String>) SerializableUtils.decode(namesData);
                    return names;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashSet<String>();
    }

    private String decKey(String key) {
        return DEFAULT_BASEPATH + "/" + key;
    }
}
