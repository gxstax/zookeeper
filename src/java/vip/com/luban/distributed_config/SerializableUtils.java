package com.luban.distributed_config;

import java.io.*;

/**
 * @ClassName SerializableUtils
 * @Description SerializableUtils
 * @Author Ant
 * @Date 2019-03-03 22:41
 * @Version 1.0
 **/
public class SerializableUtils {
    public static byte[] encode(Object object) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            oos.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static Object decode(byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            return object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
