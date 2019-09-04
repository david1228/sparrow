package com.zoo.sparrow.jdk8.concurrent;

import java.util.concurrent.*;

/**
 * Future 接口测试
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class FutureTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 像业务线程池中提交一个Callable对象
        Future<Double> future = executorService.submit(new Callable<Double>() {
            // 以异步方式在新的线程中执行耗时的操作
            @Override
            public Double call() throws Exception {
                return doSomethingLongComputation();
            }
        });

        // 异步执行耗时操作同时，可以同时搞点其他事情
        doSomethingElse();

        try {
            // 其他事情搞完，可以获取异步线程执行结果了，如果最终被阻塞，无法得到结果，那么在最多等待1秒，之后就退出了。
            // 兄弟们，这里是阻塞线程啊......
            Double result = future.get(2, TimeUnit.SECONDS);
            // 还是建议使用有超时参数的future.get方法，避免无止境的等待下去。
//            Double result = future.get();
        } catch (InterruptedException e) {
            // 当前线程在执行过程中被中断
            e.printStackTrace();
        } catch (ExecutionException e) {
            // 计算抛出一个异常
            e.printStackTrace();
        } catch (TimeoutException e) {
            // 在Future对象完成之前，超过过期时间1秒，抛出TimeoutException异常。
            e.printStackTrace();
        }

        System.out.println("全部事情都搞完，over......!!!");
    }

    private static Double doSomethingLongComputation() {
        System.out.println("我是一个耗时的操作哦，开始执行......");
        sleepSeconds(5);
        System.out.println("我是一个耗时的操作哦，执行结束啦......");
        return 22.45;
    }

    private static void doSomethingElse() {
        sleepSeconds(2);
        System.out.println("搞点其他事情......");
    }

    private static void sleepSeconds(long time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
