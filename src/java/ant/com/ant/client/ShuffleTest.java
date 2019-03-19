package com.ant.client;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName ShuffleTest
 * @Description ShuffleTest
 * @Author Ant
 * @Date 2019-03-18 22:39
 * @Version 1.0
 **/
public class ShuffleTest {
    public static void main(String[] args) {
        List<Integer> list = Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        Collections.shuffle(list);
        System.out.println(list);
    }
}
