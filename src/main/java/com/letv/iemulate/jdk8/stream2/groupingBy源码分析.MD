#### Collectors中的groupingBy源码解析

1、三个重载的groupingBy方法
2、理解每个重载方法的泛型含义
3、软件设计，对外提供都是非常好用的API，而往往底层设计较复杂，也是更灵活的。

T要操作的原始对象T类型，K是按对象T类型的哪个属性进行分组，作为Map中的键.
classifier变量输入参数? super T 表示T或者T之上的类型，返回类型? extends K表示K或者K之下的类型
toList()收集器默认是ArrayList的中间结果容器
public static <T, K> Collector<T, ?, Map<K, List<T>>>
    groupingBy(Function<? super T, ? extends K> classifier) {
        return groupingBy(classifier, toList());
    }
    
这里的K等价于Map中的键（比如Student对象，按年龄分组，这里的age就是K，D就是Stdudent对象集合-最终结果）
public static <T, K, A, D>
    Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                                          Collector<? super T, A, D> downstream) {
        return groupingBy(classifier, HashMap::new, downstream);
    } 

public static <T, K, D, A, M extends Map<K, D>>
    Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier,
                                  Supplier<M> mapFactory,
                                  Collector<? super T, A, D> downstream) {
        // 获取downstream下游的中间(A)结果容器类型的Supplier（可以理解为ArrayList）                  
        Supplier<A> downstreamSupplier = downstream.supplier();
        // 获取downstream下游累加器的对象BiConsumer，A累加器类型  ? super T元素对象类型 (可以理解为A：ArrayList<Student> T：Student)
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        // A是下游收集器中间结果类型作为Map的value类型，K是classifier中的返回类型。定义要返回的Collector收集器的accumulator累加器(传入2个参数m和t)。
        BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
            K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            // A下游的可变中间结果类型，通过获取的k，并应用到下游的supplier(如ArrayList结果容器)，简单理解构建以某一属性(如age)为key的Map，value为ArrayList
            A container = m.computeIfAbsent(key, k -> downstreamSupplier.get());
            // 应用下游的accumulator的accept方法。就是将t元素累加到container中间结果容器中。
            downstreamAccumulator.accept(container, t);
        };
        // conbiner，将多个结果容器合并为一个，合并之后的类型为Map<K, A> （可以连接为Map<Integer, List<Student>>）
        BinaryOperator<Map<K, A>> merger = Collectors.<K, A, Map<K, A>>mapMerger(downstream.combiner());
        @SuppressWarnings("unchecked")
        // 这里不是很好理解，mapFactory强转为Map<K, A>类型的Supplier，为什么呢？这里在看源码时一定要多思考下
        // 通过上下文我们知道要最终返回的Collector的BiConsumer中的中间结果容器是Map<K, A>，所以Supplier提供的容器也应该是这个类型，故JDK做了强转。
        Supplier<Map<K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;

        // 这里如果包含了IDENTITY_FINISH（同一性），则表示不执行finisher函数，中间结果容器直接转换为最终结果，类型是一样的。
        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl<>(mangledFactory, accumulator, merger, CH_ID);
        }
        else {
        // 如果是非IDENTITY_FINISH，则说明要讲中间结果容器（Map<K, A>）转换为最终我们想要的结果，即Map<K, D>
            @SuppressWarnings("unchecked")
            // 对downstream下游finisher函数做了个强转为<A, A>类型的Function。
            Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
            // 定义最终Collector的finisher，实际就是将downstream的finisher转换为最终返回Collector的finisher。
            // intermediate就是Map<K, A>
            Function<Map<K, A>, M> finisher = intermediate -> {
                // 将Map<K, A>中k和v，应用downstreamFinisher，最终转换为M类型，即Map<K, D>
                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
                @SuppressWarnings("unchecked")
                M castResult = (M) intermediate;
                return castResult;
            };
            return new CollectorImpl<>(mangledFactory, accumulator, merger, finisher, CH_NOID);
        }
    }

// 如果缺席就做计算(将新的value放到map中)
default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        // 判断map中的key对应的value是空，则应用mappingFunction(输入一个参数，返回一个结果)，
        // 如果新的结果newValue不为空，则调用put放到map中；否则，直接返回已存在的value。
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }

        return v;
    }

#####如果是并发的，不建议使用groupingBy，可以使用groupingByConcurrent，在无需和并发情况下使用。
public static <T, K>
    Collector<T, ?, ConcurrentMap<K, List<T>>>
    groupingByConcurrent(Function<? super T, ? extends K> classifier) {
        return groupingByConcurrent(classifier, ConcurrentHashMap::new, toList());
    }

public static <T, K, A, D>
    Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> classifier,
                                                              Collector<? super T, A, D> downstream) {
        return groupingByConcurrent(classifier, ConcurrentHashMap::new, downstream);
    }

public static <T, K, A, D, M extends ConcurrentMap<K, D>>
    Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier,
                                            Supplier<M> mapFactory,
                                            Collector<? super T, A, D> downstream) {
        Supplier<A> downstreamSupplier = downstream.supplier();
        BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        BinaryOperator<ConcurrentMap<K, A>> merger = Collectors.<K, A, ConcurrentMap<K, A>>mapMerger(downstream.combiner());
        @SuppressWarnings("unchecked")
        Supplier<ConcurrentMap<K, A>> mangledFactory = (Supplier<ConcurrentMap<K, A>>) mapFactory;
        BiConsumer<ConcurrentMap<K, A>, T> accumulator;
        if (downstream.characteristics().contains(Collector.Characteristics.CONCURRENT)) {
            accumulator = (m, t) -> {
                K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
                A resultContainer = m.computeIfAbsent(key, k -> downstreamSupplier.get());
                downstreamAccumulator.accept(resultContainer, t);
            };
        }
        else {
            accumulator = (m, t) -> {
                K key = Objects.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
                A resultContainer = m.computeIfAbsent(key, k -> downstreamSupplier.get());
                // 如果多线程执行，只有一个结果容器，并且同步操作的，groupingBy是会产生多个结果容器的。
                synchronized (resultContainer) {
                    downstreamAccumulator.accept(resultContainer, t);
                }
            };
        }

        if (downstream.characteristics().contains(Collector.Characteristics.IDENTITY_FINISH)) {
            return new CollectorImpl<>(mangledFactory, accumulator, merger, CH_CONCURRENT_ID);
        }
        else {
            @SuppressWarnings("unchecked")
            Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
            Function<ConcurrentMap<K, A>, M> finisher = intermediate -> {
                intermediate.replaceAll((k, v) -> downstreamFinisher.apply(v));
                @SuppressWarnings("unchecked")
                M castResult = (M) intermediate;
                return castResult;
            };
            return new CollectorImpl<>(mangledFactory, accumulator, merger, finisher, CH_CONCURRENT_NOID);
        }
    }

