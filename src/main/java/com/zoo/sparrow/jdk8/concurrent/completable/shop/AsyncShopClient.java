package com.zoo.sparrow.jdk8.concurrent.completable.shop;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author liudewei
 * @date 2019/5/12
 */
public class AsyncShopClient {

    public static void main(String[] args) {
        Shop shop = new Shop();

        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceSupplyAsync("hello world product");
//        Future<Double> futurePrice = shop.getPriceAsync("hello world product");


        long invocationTime = ((System.nanoTime() - start) / 1_000_000);

        System.out.println("invocation returned after: " + invocationTime + "msecs");

        // 执行更多任务，比如查询其他商店
        doSomethingElse();
        // 在计算商品价格的同时
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 查询商店，试图 取得商品的价格

        // 从Future对象中读 取价格，如果价格 未知，会发生阻塞
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }

    private static void doSomethingElse() {
        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("其他事情已搞完 。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
