package com.zoo.sparrow.jdk8.concurrent.completable.shop;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author liudewei
 * @date 2019/5/12
 */
public class Shop {

    // 模拟延迟1秒中
    public static void delay() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 兄弟，这是一个同步方法的操作，商店店主感觉会给用户带来很不好的用户体验，太慢了......
    public double getPrice(String product) throws IllegalAccessException {
        // 待实现，查询商店数据库，或者是一些耗时的操作
        calculatePrice(product);
        return 22.34;
    }

    // 那么，我们通过CompletableFuture来改写一下，异步计算价格
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futureService = new CompletableFuture<>();

        // 启动一个线程，异步执行计算价格的任务
        new Thread(() -> {
            double price = 0;
            try {
                price = calculatePrice(product);
                futureService.complete(price);
            } catch (Exception e) {
                // 通过completeExceptionally方法传递异常，就能知道这个线程到底发生了什么
                futureService.completeExceptionally(e);
//                e.printStackTrace();
            }

        }).start();

        // 无需等待计算完成，直接返回Future对象实例
        return futureService;
    }

    // 使用工厂方法supplyAsync方法，并通过exceptionally处理异常
    public Future<Double> getPriceSupplyAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product)).exceptionally(ex -> {
             ex.printStackTrace();
             return 0.00;
        });
    }

    public double calculatePrice(String product) {
        delay();
        Random random = new Random();
        if (true) {
            throw new RuntimeException("哈哈，我不想计算价格了，异常咯...!!!，同时呢，我也把当前线程给杀死了, sorry");
        }
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

}
