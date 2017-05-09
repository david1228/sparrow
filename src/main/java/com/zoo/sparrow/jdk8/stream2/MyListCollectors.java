package com.zoo.sparrow.jdk8.stream2;

import com.google.common.collect.Sets;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.UNORDERED;

/**
 * 自定义收集器，实现案例：集合中的字符串元素结果去重。
 * 1) 首先实现Collector接口以及重写其接口中的5个关键函数：
 *    supplier() accumulator() combiner() finisher() characteristics()
 *
 * 2) 构建中间操作涉及的结果容器
 *
 * Created by David.Liu on 17/4/20.
 */
public class MyListCollectors<T> implements Collector<T, List<T>, Set<T>> {

    /**
     * 创建一个结果容器 【无参数，返回一个结果】
     *
     * @return
     */
    @Override public Supplier<List<T>> supplier() {
        System.out.println("supplier invoked!");
        return ArrayList<T>::new;
        // return () -> new ArrayList<T>(); // 等价表达式
    }

    /**
     * 将下一个元素添加到结果容器中  【传入2个参数，void无返回结果】
     * 不能使用LinkedList<T>::add 原因是与上下文环境中即supplier创建的容器类型不符，只能是其超类如List<T>
     *
     * @return
     */
    @Override public BiConsumer<List<T>, T> accumulator() {
        System.out.println("accumulator invoked!");
        return List<T>::add;
        // return (list, item) -> list.add(item); // 等价表达式
    }

    /**
     * 并行合并操作；将多个子结果容器merge到一个结果容器中  【输入2个参数，返回一个结果】
     *
     * @return
     */
    @Override public BinaryOperator<List<T>> combiner() {
        System.out.println("combiner invoked!");
        return (list, list2) -> {list.addAll(list2); return list;}; // 方法引用搞不定，只能直接写lambda表达式了
    }

    /**
     * 可选的最终转换；【输入1个参数，返回一个结果】
     *
     * @return  期望返回List<T>
     */
    @Override public Function<List<T>, Set<T>> finisher() {
        System.out.println("finisher invoked!");
//        return Function.identity(); // 返回一个等同于输入参数的结果，当A、R两个类型不一样，则不适用identity
        return (list) -> {
            return Sets.newHashSet(list); // A、R类型不同的示例，A类型为List<T> R类型为Set<T>
        };
        //        return t -> t;
    }

    /**
     * 收集器的特性，优化聚合操作
     *
     * @return
     */
    @Override public Set<Characteristics> characteristics() {
        System.out.println("characteristics invoked!");
//        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
//        return Collections.unmodifiableSet(EnumSet.of(IDENTITY_FINISH, UNORDERED));
        return Collections.unmodifiableSet(EnumSet.of(UNORDERED));
    }

    // 测试

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "welcom", "hello worlds");

        Set<String> result = list.stream().collect(new MyListCollectors<String>());

        result.forEach(System.out::println);

        // 具体详解：
        /*
        我们看到了输出结果为：
        supplier invoked!
        accumulator invoked!
        combiner invoked!
        characteristics invoked!
        characteristics invoked!
        welcom
        world
        hello worlds
        hello

        1）为什么会打印两次characteristics invoked!，通过collect源码可以看到

        public static <T, I> TerminalOp<T, I>
    makeRef(Collector<? super T, I, ?> collector) {
        Supplier<I> supplier = Objects.requireNonNull(collector).supplier();
        BiConsumer<I, ? super T> accumulator = collector.accumulator();
        BinaryOperator<I> combiner = collector.combiner();
        class ReducingSink extends Box<I>
                implements AccumulatingSink<T, I, ReducingSink> {
            @Override
            public void begin(long size) {
                state = supplier.get();
            }

            @Override
            public void accept(T t) {
                accumulator.accept(state, t);
            }

            @Override
            public void combine(ReducingSink other) {
                state = combiner.apply(state, other.state);
            }
        }
        return new ReduceOp<T, I, ReducingSink>(StreamShape.REFERENCE) {
            @Override
            public ReducingSink makeSink() {
                return new ReducingSink();
            }

            @Override
            public int getOpFlags() {
                return collector.characteristics().contains(Collector.Characteristics.UNORDERED)
                       ? StreamOpFlag.NOT_ORDERED
                       : 0;
            }
        };
    }

    makeRef方法中new ReduceOp中的getOpFlags会判断是否是无需的返回个操作标识flag

    然后在：
    public final <R, A> R collect(Collector<? super P_OUT, A, R> collector) {
        A container;
        if (isParallel()
                && (collector.characteristics().contains(Collector.Characteristics.CONCURRENT))
                && (!isOrdered() || collector.characteristics().contains(Collector.Characteristics.UNORDERED))) {
            container = collector.supplier().get();
            BiConsumer<A, ? super P_OUT> accumulator = collector.accumulator();
            forEach(u -> accumulator.accept(container, u));
        }
        else {
            container = evaluate(ReduceOps.makeRef(collector));
        }
        return collector.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)
               ? (R) container
               : collector.finisher().apply(container);
    }
    返回结果中再一次判断是否是identity同一性的
    如果是则直接做强制类型转换(R) container，否则，将调用收集器的finisher函数完成最终转换操作
    注意:这里没有做类型检查，所以我们自定义收集器的A和R类型必须一致。 否则，将会出现ClassCastException异常。
    Exception in thread "main" java.lang.ClassCastException: java.util.ArrayList cannot be cast to java.util.Set
	at MyListCollectors.main(MyListCollectors.java:89)


    当A、R类型不同，A:List<T> R:Set<T>，并且characteristics函数只有UNORDER特性，不能指定IDENTITY_FINISH
    打印结果如下：
    supplier invoked!
    accumulator invoked!
    combiner invoked!
    characteristics invoked!
    characteristics invoked!
    finisher invoked!
    welcom
    world
    hello worlds
    hello
    打印了finisher invoked!，说明指定了finisher函数完成最终转换的操作。
    */
    }
}
