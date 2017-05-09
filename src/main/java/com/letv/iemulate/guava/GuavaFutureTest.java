package com.letv.iemulate.guava;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by David.Liu on 17/5/8.
 */
public class GuavaFutureTest {

    public static void main(String[] args) {
        getObjectsByPartition();

        //        partitionList.stream().flatMap();
    }

    public static void getObjectsByPartition() {
        long start = System.currentTimeMillis();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1));
        // Executor executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        // 分隔集合
        List<Integer> ids = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        List<List<Integer>> partitionList = Lists.partition(ids, 3);

        List<ListenableFuture<Map<String, Object>>> listenableFutures = Lists.newArrayList();
        for (List<Integer> partionItem : partitionList) {
            ListenableFuture<Map<String, Object>> explosion = service.submit(new Callable<Map<String, Object>>() {
                @Override public Map<String, Object> call() throws Exception {
                    // cache get partionItem
                    Map<String, Object> m = Maps.newHashMap();
                    for (Integer item : partionItem) {
                        m.put(String.valueOf(item), partionItem + "_xxx");
                    }
                    Thread.sleep(300);
                    return m;
                }
            });

            listenableFutures.add(explosion);
        }

        Map<String, Object> maps = Maps.newHashMap();
        ListenableFuture<List<Map<String, Object>>> futures = Futures.allAsList(listenableFutures);
        try {
            for (Map<String, Object> obj : futures.get(300, TimeUnit.MILLISECONDS)) {
                if (obj != null) {
                    maps.putAll(obj);
                }
            }
        } catch (Exception e) {
            futures.cancel(true);
        }

        List<Object> result = Lists.newArrayList();
        // 按顺序聚合
        for (Integer idItem : ids) {
            result.add(maps.get(idItem));
            System.out.println("最终结果:" + idItem);
        }
        long end = System.currentTimeMillis();
        System.out.println("time:" + String.valueOf(end - start));
    }

    /**
     * 适合用于非高并发切对查询效率有要求的情况下使用。
     *
     */
    public static void videoPartition () {
        // mget 并发分组执行
        /*int group = 10, timeout = 1000;
        List<List<Long>> partitionIdList = Lists.partition(ids, group);
        List<ListenableFuture<Map<String, T>>> listenableFutures = Lists.newArrayList();
        for (final List<Long> partitionIds : partitionIdList) {
            ListenableFuture<Map<String, T>> explosion = poolService.submit(new Callable<Map<String, T>>() {
                @Override public Map<String, T> call() throws Exception {
                    return cacheService.get(key, partitionIds, clazz);
                }
            });

            listenableFutures.add(explosion);
        }

        Map<String, T> resultMap = Maps.newHashMap();
        ListenableFuture<List<Map<String, T>>> futures = Futures.allAsList(listenableFutures);
        try {
            for (Map<String, T> t : futures.get(timeout, TimeUnit.MILLISECONDS)) {
                if (t != null) {
                    resultMap.putAll(t);
                }
            }
        } catch (Exception e) { // TODO 具体异常
            LOG.error("get result from cache by partition keys error:{}", e.getMessage(), e);
            futures.cancel(true);
        }

        list = new ArrayList<T>(ids.size());
        for (Long id : ids) {
            list.add((T) resultMap.get(key + id.toString()));
        }*/
    }


}
