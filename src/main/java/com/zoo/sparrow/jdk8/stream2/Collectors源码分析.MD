### 底层源码分析篇
#### Collectors工具类源码分析

对于Collectors静态工厂类来说，一共分为两种情况：
1. 通过CollectorImpl来实现。
2. 通过reducing方法来实现，reducing方法本身又是通过CollectorImpl实现的。
所以归根结底，Collectors底层都是通过CollectorImpl实现的。

<<<<<<< HEAD
####集合方法详解：
#####toCollection<br>(toList和toSet只是toCollection方法的一种特化)
=======
#### 集合方法详解：
##### toCollection<br>(toList和toSet只是toCollection方法的一种特化)
>>>>>>> bad4c6281d31844da2b6577c98597ad24d8e3fc2
````
public static <T, C extends Collection<T>>
    Collector<T, ?, C> toCollection(Supplier<C> collectionFactory) {
        return new CollectorImpl<>(collectionFactory, Collection<T>::add,
                                   (r1, r2) -> { r1.addAll(r2); return r1; },
                                   CH_ID);
    }
````
可以直接传入collectionFactory即可。

<<<<<<< HEAD
#####toList
=======
##### toList
>>>>>>> bad4c6281d31844da2b6577c98597ad24d8e3fc2
````
public static <T>
    Collector<T, ?, List<T>> toList() {
        return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, List::add,
                                   (left, right) -> { left.addAll(right); return left; },
                                   CH_ID);
    }
````
(Supplier<List<T>>) ArrayList::new 中间结果容器，JDK中做了下强转，实际可以用ArrayList<T>::new。
List::add 将元素添加到中间结果容器
(left, right) -> { left.addAll(right); return left; }  将结果容器合并到新的结果容器
CH_ID 表示Characteristics.IDENTITY_FINISHER特性，默认是同一性的。

<<<<<<< HEAD
#####toSet
=======
##### toSet
>>>>>>> bad4c6281d31844da2b6577c98597ad24d8e3fc2
````
public static <T>
    Collector<T, ?, Set<T>> toSet() {
        return new CollectorImpl<>((Supplier<Set<T>>) HashSet::new, Set::add,
                                   (left, right) -> { left.addAll(right); return left; },
                                   CH_UNORDERED_ID);
    }
````
(Supplier<Set<T>>) HashSet::new 中间结果容器，JDK中做了下强转，实际可以用HashSet<T>::new。
Set::add 将元素添加到中间结果容器
(left, right) -> { left.addAll(right); return left; }  将结果容器合并到新的结果容器
CH_UNORDERED_ID 表示Characteristics.UNORDERED、Characteristics.IDENTITY_FINISHER特性，也就是无序并且具有同一性的。
   
<<<<<<< HEAD
####Join拼接方法详解
#####joining
=======
#### Join拼接方法详解
##### joining
>>>>>>> bad4c6281d31844da2b6577c98597ad24d8e3fc2
````
public static Collector<CharSequence, ?, String> joining() {
        return new CollectorImpl<CharSequence, StringBuilder, String>(
                StringBuilder::new, StringBuilder::append,
                (r1, r2) -> { r1.append(r2); return r1; },
                StringBuilder::toString, CH_NOID);
    }
````
new CollectorImpl<CharSequence, StringBuilder, String> 指定了泛型，CharSequence原对象类型 StringBuilder中间结果容器类型 String最终结果类型。
其他不做解释，特殊说下CH_NOID，任何的Characteristic的特性都不具备，也很好理解，拼接结果一定是有序的。

还有两个重载方法：<br>
````
public static Collector<CharSequence, ?, String> joining(CharSequence delimiter,
                                                             CharSequence prefix,
                                                             CharSequence suffix) {
        return new CollectorImpl<>(
                () -> new StringJoiner(delimiter, prefix, suffix),
                StringJoiner::add, StringJoiner::merge,
                StringJoiner::toString, CH_NOID);
    }
````
（StringJoiner是JDK8新提供的一个类，目的就是为了方便字符串的拼接操作。）

````
public static Collector<CharSequence, ?, String> joining(CharSequence delimiter) {
        return joining(delimiter, "", "");
    }
````    
<<<<<<< HEAD
####mapping方法详解
#####mapping
=======
#### mapping方法详解
##### mapping
>>>>>>> bad4c6281d31844da2b6577c98597ad24d8e3fc2
````
public static <T, U, A, R>
    Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper,
                               Collector<? super U, A, R> downstream) {
        BiConsumer<A, ? super U> downstreamAccumulator = downstream.accumulator();
        return new CollectorImpl<>(downstream.supplier(),
                                   (r, t) -> downstreamAccumulator.accept(r, mapper.apply(t)),
                                   downstream.combiner(), downstream.finisher(),
                                   downstream.characteristics());
    }
````
downstream下游就是你自己指定的收集器类型。
suppllier、combiner、finisher、characteristic都是调用的downstream下游收集器。
而accumulator特殊(r, t) -> downstreamAccumulator.accept(r, mapper.apply(t))
意思是将一个U类型（比如Person）转换为T类型（比如Person中的lastName字符串类型属性）通过mapper.apply函数来实现。
代码示例：
````
Map<City, Set<String>> lastNamesByCity
         = people.stream().collect(groupingBy(Person::getCity,
                                              mapping(Person::getLastName, toSet())));
````                                           
那么上述示例中的downstream下游就是toSet()方法

<<<<<<< HEAD
#####collectingAndThen
=======
##### collectingAndThen
>>>>>>> bad4c6281d31844da2b6577c98597ad24d8e3fc2
````
public static<T,A,R,RR> Collector<T,A,RR> collectingAndThen(Collector<T,A,R> downstream,
                                                                Function<R,RR> finisher) {
        Set<Collector.Characteristics> characteristics = downstream.characteristics();
        if (characteristics.contains(Collector.Characteristics.IDENTITY_FINISH)) {
            if (characteristics.size() == 1)
                characteristics = Collectors.CH_NOID;
            else {
                characteristics = EnumSet.copyOf(characteristics);
                characteristics.remove(Collector.Characteristics.IDENTITY_FINISH);
                characteristics = Collections.unmodifiableSet(characteristics);
            }
        }
        return new CollectorImpl<>(downstream.supplier(),
                                   downstream.accumulator(),
                                   downstream.combiner(),
                                   downstream.finisher().andThen(finisher),
                                   characteristics);
    }
````
此方法实现中我们看到将IDETITY_FINISHER去掉了，为什么呢？
因为downstream.finisher().andThen(finisher)意思是对于collectingAndThen是执行最后一个finisher函数的。
所以如果想要执行finisher函数，那么就不能设置IDENTITY_FINISHER特性，因为stream().collect方法中如果具有同一性的，是不会调用finisher函数的。