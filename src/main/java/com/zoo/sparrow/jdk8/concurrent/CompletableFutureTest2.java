package com.zoo.sparrow.jdk8.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Supplier;

/**
 * CompletableFuture源码分析：https://www.cnblogs.com/aniao/p/aniao_cf.html
 *
 * Created by David.Liu on 17/8/9.
 */
public class CompletableFutureTest2 {

    public static void main(String[] args) {
        CompletableFuture<Double> completableFuture = getPriceAync();

        //do anything you want, 当前线程不被阻塞
        System.out.println(111);


        CompletableFuture.supplyAsync(new Supplier<String>(){
            @Override
            public String get() {
                return null;
            }
        });

        //线程任务完成的话，执行回调函数，不阻塞后续操作
        completableFuture.whenComplete((price, throwable) -> {
            System.out.println("complete price:" + price);
        });

        System.out.println(222);

        boolean useCommonPool =
                (ForkJoinPool.getCommonPoolParallelism() > 1);
        System.out.println(useCommonPool);
    }

    static CompletableFuture<Double> getPriceAync() {
        CompletableFuture<Double> completableFuture = new CompletableFuture<>();
//        return completableFuture.supplyAsync(() -> getPrice());

        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            completableFuture.complete(getPrice());
        }).start();
        return completableFuture;
    }

    static Double getPrice(){
        return 22.35;
    }
}
