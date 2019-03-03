package com.luban.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName ShuffleTest
 * @Description ShuffleTest
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class ShuffleTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        Collections.shuffle(list);
        System.out.println(list);


    }
}
