package com.zoo.sparrow.classload;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by David.Liu on 17/8/30.
 */
public class TestList {

    @Test public void test() {
        ClassLoader classLoader = new ClassLoader() {
            @Override public Class<?> loadClass(String name) throws ClassNotFoundException {

                System.out.println("加载类..." + name);
                String className = name.substring(name.lastIndexOf(".") + 1) + "class";
                InputStream inputStream = getClass().getResourceAsStream(className);
                System.out.println(inputStream);
                if (null == inputStream) {
                    return super.loadClass(name);
                }

                System.out.println("自定义加载类....");
                try {
                    int len = inputStream.available();
                    byte[] bytes = new byte[len];
                    inputStream.read(bytes);
                    return super.defineClass(name, bytes, 0, bytes.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                throw new ClassNotFoundException("类没有找到...");
            }
        };

        try {
            Object loadClass = classLoader.loadClass("com.zoo.sparrow.classload.TestList").newInstance();
            System.out.println(loadClass.getClass());
            System.out.println(loadClass instanceof TestList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
