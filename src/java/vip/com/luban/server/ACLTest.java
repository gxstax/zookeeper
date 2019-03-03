package com.luban.server;

import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;

/**
 * *************书山有路勤为径***************
 * 鲁班学院
 * 往期资料加木兰老师  QQ: 2746251334
 * VIP课程加安其拉老师 QQ: 3164703201
 * 讲师：周瑜老师
 * *************学海无涯苦作舟***************
 */
public class ACLTest {

    public static void main(String[] args) {

        ACL acl1 = new ACL(1, new Id("auth", "zhangsan:12345"));
        ACL acl2 = new ACL(2, new Id("auth", "zhangsan:12345"));

        System.out.println(acl1.equals(acl2));
    }
}
