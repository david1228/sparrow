package com.zoo.sparrow.jdk8.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * 参考：https://yanbin.blog/completablefuture-concurrent-performance/#more-7669
 * 针对Stream、ParallelStream、DefaultExecutorCompletableFuture、CustomExecutorCompletableFuture之间的性能对比
 * 我的Mac本机是 8 Processors
 * 测试结果：
 * Processors: 8 ForkJoinPool 线程池大小是20
 * When 5 tasks => stream: 5017, parallelStream: 1013, future default: 1004, future custom: 1002
 * When 7 tasks => stream: 7022, parallelStream: 1005, future default: 1002, future custom: 1002
 * When 8 tasks => stream: 8026, parallelStream: 1005, future default: 2006, future custom: 1005
 * When 9 tasks => stream: 9034, parallelStream: 2007, future default: 2007, future custom: 1006
 * When 10 tasks => stream: 10024, parallelStream: 2010, future default: 2009, future custom: 1004
 * When 12 tasks => stream: 12029, parallelStream: 2008, future default: 2007, future custom: 1005
 * When 13 tasks => stream: 13035, parallelStream: 2003, future default: 2005, future custom: 1006
 * When 18 tasks => stream: 18058, parallelStream: 3013, future default: 3008, future custom: 1008
 * When 24 tasks => stream: 24082, parallelStream: 3010, future default: 4014, future custom: 2007
 * When 25 tasks => stream: 25068, parallelStream: 4010, future default: 4010, future custom: 2009
 *
 *
 * Processors: 8 Executors.newFixedThreadPool(PROCESSORS * 2 + 1) 线程池大小是Processors * 2 + 1 = 19
 * When 5 tasks => stream: 5021, parallelStream: 1011, future default: 1003, future custom: 1003
 * When 7 tasks => stream: 7026, parallelStream: 1005, future default: 1004, future custom: 1004
 * When 8 tasks => stream: 8019, parallelStream: 1005, future default: 2005, future custom: 1002
 * When 9 tasks => stream: 9020, parallelStream: 2006, future default: 2008, future custom: 1005
 * When 10 tasks => stream: 10030, parallelStream: 2006, future default: 2008, future custom: 1004
 * When 12 tasks => stream: 12030, parallelStream: 2006, future default: 2003, future custom: 1005
 * When 13 tasks => stream: 13039, parallelStream: 2005, future default: 2002, future custom: 1004
 * When 18 tasks => stream: 18058, parallelStream: 3009, future default: 3013, future custom: 2005
 * When 24 tasks => stream: 24061, parallelStream: 3012, future default: 4012, future custom: 2004
 * When 25 tasks => stream: 25075, parallelStream: 4011, future default: 4008, future custom: 2005
 *
 * 自定义ThreadPoolExecutor线程池，核心线程数CPU个数*2，最大线程数100，使用ArrayBlockingQueue作为队列，自定义工厂，拒接策略选择CallerRunsPolicy
 * Processors: 8
 * When 5 tasks => stream: 5019, parallelStream: 1013, future default: 1004, future custom: 1005
 * When 7 tasks => stream: 7027, parallelStream: 1002, future default: 1004, future custom: 1005
 * When 8 tasks => stream: 8018, parallelStream: 1004, future default: 2008, future custom: 1004
 * When 9 tasks => stream: 9031, parallelStream: 2008, future default: 2009, future custom: 1003
 * When 10 tasks => stream: 10022, parallelStream: 2006, future default: 2005, future custom: 1001
 * When 12 tasks => stream: 12037, parallelStream: 2007, future default: 2005, future custom: 1005
 * When 13 tasks => stream: 13032, parallelStream: 2005, future default: 2008, future custom: 1004
 * When 18 tasks => stream: 18040, parallelStream: 3008, future default: 3007, future custom: 2006
 * When 24 tasks => stream: 24077, parallelStream: 3006, future default: 4010, future custom: 2007
 * When 25 tasks => stream: 25058, parallelStream: 4011, future default: 4008, future custom: 2007
 *
 * 线程池核心大小为80
 * Processors: 8
 * When 5 tasks => stream: 5016, parallelStream: 1008, future default: 1001, future custom: 1004
 * When 7 tasks => stream: 7024, parallelStream: 1002, future default: 1004, future custom: 1001
 * When 8 tasks => stream: 8020, parallelStream: 1000, future default: 2009, future custom: 1001
 * When 9 tasks => stream: 9016, parallelStream: 2008, future default: 2006, future custom: 1005
 * When 10 tasks => stream: 10033, parallelStream: 2006, future default: 2008, future custom: 1000
 * When 12 tasks => stream: 12035, parallelStream: 2006, future default: 2004, future custom: 1001
 * When 13 tasks => stream: 13026, parallelStream: 2008, future default: 2008, future custom: 1004
 * When 18 tasks => stream: 18058, parallelStream: 3005, future default: 3010, future custom: 1004
 * When 24 tasks => stream: 24073, parallelStream: 3006, future default: 4009, future custom: 1002
 * When 25 tasks => stream: 25077, parallelStream: 4010, future default: 4010, future custom: 1000
 * When 38 tasks => stream: 38117, parallelStream: 5020, future default: 6018, future custom: 1005
 * When 58 tasks => stream: 58167, parallelStream: 8030, future default: 9028, future custom: 1002
 * When 108 tasks => stream: 108348, parallelStream: 15049, future default: 16054, future custom: 2001
 *
 * 第二次，最多208个tasks
 * Processors: 8
 * When 5 tasks => stream: 5020, parallelStream: 1011, future default: 1003, future custom: 1006
 * When 7 tasks => stream: 7016, parallelStream: 1004, future default: 1002, future custom: 1002
 * When 8 tasks => stream: 8028, parallelStream: 1004, future default: 2009, future custom: 1005
 * When 9 tasks => stream: 9021, parallelStream: 2001, future default: 2008, future custom: 1003
 * When 10 tasks => stream: 10026, parallelStream: 2008, future default: 2006, future custom: 1002
 * When 12 tasks => stream: 12040, parallelStream: 2008, future default: 2008, future custom: 1000
 * When 13 tasks => stream: 13032, parallelStream: 2006, future default: 2006, future custom: 1004
 * When 18 tasks => stream: 18053, parallelStream: 3009, future default: 3010, future custom: 1001
 * When 24 tasks => stream: 24088, parallelStream: 3011, future default: 4009, future custom: 1005
 * When 25 tasks => stream: 25089, parallelStream: 4014, future default: 4009, future custom: 1002
 * When 38 tasks => stream: 38091, parallelStream: 5016, future default: 6019, future custom: 1002
 * When 58 tasks => stream: 58155, parallelStream: 8035, future default: 9016, future custom: 1004
 * When 108 tasks => stream: 108329, parallelStream: 15039, future default: 16046, future custom: 2000
 * When 158 tasks => stream: 158469, parallelStream: 20062, future default: 23073, future custom: 2004
 * When 208 tasks => stream: 208646, parallelStream: 27085, future default: 30092, future custom: 3005
 *
 * 第一次，自定义核心线程数 200
 * Processors: 8
 * When 5 tasks => stream: 5022, parallelStream: 1011, future default: 1006, future custom: 1005
 * When 7 tasks => stream: 7025, parallelStream: 1001, future default: 1003, future custom: 1005
 * When 8 tasks => stream: 8022, parallelStream: 1003, future default: 2008, future custom: 1002
 * When 9 tasks => stream: 9029, parallelStream: 2006, future default: 2008, future custom: 1001
 * When 10 tasks => stream: 10023, parallelStream: 2003, future default: 2009, future custom: 1004
 * When 12 tasks => stream: 12039, parallelStream: 2006, future default: 2009, future custom: 1003
 * When 13 tasks => stream: 13036, parallelStream: 2002, future default: 2007, future custom: 1003
 * When 18 tasks => stream: 18055, parallelStream: 3010, future default: 3010, future custom: 1003
 * When 24 tasks => stream: 24071, parallelStream: 3008, future default: 4013, future custom: 1004
 * When 25 tasks => stream: 25081, parallelStream: 4011, future default: 4009, future custom: 1005
 * When 38 tasks => stream: 38120, parallelStream: 5021, future default: 6016, future custom: 1005
 * When 58 tasks => stream: 58189, parallelStream: 8039, future default: 9026, future custom: 1004
 * When 108 tasks => stream: 108328, parallelStream: 14048, future default: 16061, future custom: 1003
 * When 158 tasks => stream: 158488, parallelStream: 20070, future default: 23079, future custom: 1004
 * When 208 tasks => stream: 208698, parallelStream: 27086, future default: 30090, future custom: 1986
 *
 * 第一次，自定义核心线程数 200
 *
 * 最终结论是 CustomExecutorCompletableFuture性能最好，所以生产环境还是要自定义线程池去使用。
 * 线程池的CPU个数计算方法，可以参考Java并发编程实战中的说明。
 *
 *
 * @author liudewei
 * @date 2019/5/12
 */
public class CompleteableFuturePerfTest {

    private static int PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        System.out.println("Processors: " + PROCESSORS);

        Arrays.asList(-3, -1, 0, 1, 2, 4, 5, 10, 16, 17, 30, 50, 100, 150, 200, 300).forEach(offset -> {
            int jobNum = PROCESSORS + offset;
            System.out.println(
                    String.format("When %s tasks => stream: %s, parallelStream: %s, future default: %s, future custom: %s",
                            jobNum, testStream(jobNum), testParallelStream(jobNum),
                            testCompletableFutureDefaultExecutor(jobNum), testCompletableFutureCustomExecutor(jobNum)));
        });

    }

    private static long testStream(int jobCount) {
        List<Supplier<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(CompleteableFuturePerfTest::getJob));

        long start = System.currentTimeMillis();
        int sum = tasks.stream().map(Supplier::get).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }

    private static long testParallelStream(int jobCount) {
        List<Supplier<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(CompleteableFuturePerfTest::getJob));

        long start = System.currentTimeMillis();
        int sum = tasks.parallelStream().map(Supplier::get).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }

    private static long testCompletableFutureDefaultExecutor(int jobCount) {
        List<CompletableFuture<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(CompletableFuture.supplyAsync(CompleteableFuturePerfTest::getJob)));

        long start = System.currentTimeMillis();
        int sum = tasks.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }

    private static long testCompletableFutureCustomExecutor(int jobCount) {
        Executor executor = new ForkJoinPool(40);
//        Executor executor = Executors.newFixedThreadPool(PROCESSORS * 2 + 1, new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                Thread thread = new Thread(r);
//                thread.setName("CUSTOM_DAEMON_COMPLETABLEFUTURE");
//                thread.setDaemon(true);
//                return thread;
//            }
//        });

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 200, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("CUSTOM_DAEMON_COMPLETABLEFUTURE");
                thread.setDaemon(true);
                return thread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy());

        List<CompletableFuture<Integer>> tasks = new ArrayList<>();
        IntStream.rangeClosed(1, jobCount).forEach(value -> tasks.add(CompletableFuture.supplyAsync(CompleteableFuturePerfTest::getJob, threadPoolExecutor)));

        long start = System.currentTimeMillis();
        int sum = tasks.stream().map(CompletableFuture::join).mapToInt(Integer::intValue).sum();
        checkSum(sum, jobCount);
        return System.currentTimeMillis() - start;
    }

    private static int getJob() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }

        return 50;
    }

    private static void checkSum(int sum, int jobNum) {
        if (sum != 50 * jobNum) {
            throw new AssertionError("Result Error");
        }
    }
}
