package com.zoo.sparrow.jdk8.stream2;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 自定义收集器，实现案例：将集合中元素去重后转换为Map<String, String>
 *
 *
 * Created by David.Liu on 17/4/23.
 */
public class MySetCollectors2<T> implements Collector<T, Set<T>, Map<T, T>> {

    /**
     *     A a1 = supplier.get();
     *     accumulator.accept(a1, t1);
     *     accumulator.accept(a1, t2);
     *     R r1 = finisher.apply(a1);  // result without splitting
     *
     *     A a2 = supplier.get();
     *     accumulator.accept(a2, t1);
     *     A a3 = supplier.get();
     *     accumulator.accept(a3, t2);
     *     R r2 = finisher.apply(combiner.apply(a2, a3));  // result with splitting
     *
     * @return
     */
    @Override public Supplier<Set<T>> supplier() {
        System.out.println("supplier invoked!");
        return () -> {
            Set<T> set = new HashSet<T>();
            System.out.println("supplier生成中间结果容器：" + set);
            return set;
        };
    }

    /**
     * 这里如果打印了set参数，并且设置了Characteristics.CONCURRENT特性，则运行中偶尔会出现java.util.ConcurrentModificationException异常。
     Exception in thread "main" java.util.ConcurrentModificationException: java.util.ConcurrentModificationException
     at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
     at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
     at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
     at java.lang.reflect.Constructor.newInstance(Constructor.java:423)
     at java.util.concurrent.ForkJoinTask.getThrowableException(ForkJoinTask.java:593)
     at java.util.concurrent.ForkJoinTask.reportException(ForkJoinTask.java:677)
     at java.util.concurrent.ForkJoinTask.invoke(ForkJoinTask.java:735)
     at java.util.stream.ForEachOps$ForEachOp.evaluateParallel(ForEachOps.java:160)
     at java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateParallel(ForEachOps.java:174)
     at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:233)
     at java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:418)
     at java.util.stream.ReferencePipeline$Head.forEach(ReferencePipeline.java:583)
     at java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:496)
     at MySetCollectors2.main(MySetCollectors2.java:69)
     Caused by: java.util.ConcurrentModificationException
     at java.util.HashMap$HashIterator.nextNode(HashMap.java:1437)
     at java.util.HashMap$KeyIterator.next(HashMap.java:1461)
     at java.util.AbstractCollection.toString(AbstractCollection.java:461)
     at java.lang.String.valueOf(String.java:2994)
     at java.lang.StringBuilder.append(StringBuilder.java:131)
     at MySetCollectors2.lambda$accumulator$0(MySetCollectors2.java:28)
     at java.util.stream.ReferencePipeline.lambda$collect$1(ReferencePipeline.java:496)
     at java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
     at java.util.Spliterators$ArraySpliterator.forEachRemaining(Spliterators.java:948)
     at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:481)
     at java.util.stream.ForEachOps$ForEachTask.compute(ForEachOps.java:291)
     at java.util.concurrent.CountedCompleter.exec(CountedCompleter.java:731)
     at java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:289)
     at java.util.concurrent.ForkJoinPool$WorkQueue.runTask(ForkJoinPool.java:1056)
     at java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1692)
     at java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:157)

     是因为设置set调用了toString方法，遍历的同时也有另外的线程对同一结果容器set做修改，set.add(item). 所以出现了上述异常。
     所以为避免出现这个异常，不要打印中间结果对象set.
     * @return
     */
    @Override public BiConsumer<Set<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
//        return Set::add;
        return (set, item) -> {
//            System.out.println("accumulator当前执行线程名称: " + Thread.currentThread().getName() + ", set中间结果容器：" + set);
            System.out.println("accumulator当前执行线程名称: " + Thread.currentThread().getName());
            set.add(item);
        };
    }

    @Override public BinaryOperator<Set<T>> combiner() {
        System.out.println("combiner invoked!");
        return (setResult, subSet) -> {
            System.out.println("combiner子结果容器subSet:" + subSet + ", 最终结果容器setResult:" + setResult);
            setResult.addAll(subSet);
            return setResult;
        };
    }

    @Override public Function<Set<T>, Map<T, T>> finisher() {
        System.out.println("finisher invoked!");
        return (Set<T> set) -> {
            Map<T, T> map = new ConcurrentHashMap<T, T>(); // 有序结果
            set.stream().forEach( item -> map.put(item, item));
            return map;
        };
    }

    @Override public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked!");
        /**
         * 设置了CONCURRENT的作用，当设置后，并不会执行combiner函数，为什么呢？
         * 根据Characteristics.CONCURRENT解释，当设置后意味着以多线程的方式操作同一结果容器；
         * 不设置Characteristics.CONCURRENT，则表示使用多个不同的结果容器，然后通过combiner函数将多个结果容器合并为一个最终结果容器；
         */
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c", "d", "hello", "world", "welcom", "hello world");
        // 普通stream流
//        Map<String, String> map = list.stream().collect(new MySetCollectors2<String>());
//        map.forEach( (k, v) -> System.out.println("k:" + k + ", v:" + v));

        // 并行stream流
        for (int i = 0; i < 100; i++) {
            Map<String, String> parallelMap = list.parallelStream().collect(new MySetCollectors2<String>());
            parallelMap.forEach( (k, v) -> System.out.println("k:" + k + ", v:" + v));
        }

        //
        list.stream().sequential().parallel().sequential().parallel().count();

    }
}
