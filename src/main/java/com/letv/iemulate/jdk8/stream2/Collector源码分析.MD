### 底层源码分析篇
#### Collector源码分析
e.g: list.stream().collect(Collectors.toList());
collect是个收集器，collect方法特别的重要，排在JDK8新增的特性中的top3
Collector作为collect方法的参数
Collector是一个很重要的组件
Collectors是个工具类，包含了针对Collector的大量的静态方法，提供了很多的常见的实现。

##### Collector源码解读：
Collector是个可变的汇聚操作，将输入元素累积到一个可变的结果容器中，
它会在所有元素都处理完毕之后，将累积的结果转换为一个最终的表示（这是一个可选操作）；
它支持串行(一个线程)与并行(多线程，结果正确，同步)两种方式执行。
【注意：并行不一定比串行快，线程上下文的切换；根据实际情况和测试结果来选择并行还是串行】

比如：
累积到Collection集合中
用StringBuilder拼接字符串
调用sum min max average等方法的操作
pivot 计算数据透视图，每个销售商，每个组最大的交易数量值
Collectors提供了很多可变汇聚操作的实现

一个Collector里面包含了4个函数，这4个函数协同工作将每个条目累积到可变的结果容器中，并且可选的执行一个最终的转换。
4个函数：supplier() accumulator() combiner() finisher()
创建一个新的结果容器，supplier函数
【返回一个Supplier对象，不接受参数返回一个结果】
将新的数据元素合并到一个结果容器，accumulator函数
【称作累加器，这个结果容器是supplier来创建的，flods等价于reduce，折叠的意思，将一个值折叠到可变的结果容器中，就是将一个元素做完处理放到List类似的容器，它返回BiConsumer，接受两个参数不返回结果】
将两个结果容器给合并为一个， combiner函数
【一个函数接受两个部分结果，将其合并起来，它将一个状态从一个参数折叠为另外一个并返回另外一个结果？BinaryOperator继承了BiFunction，两个输入参数和返回结果都是T类型对象】
【用在并行流，比如有4个线程同时去执行，那么就会生生4个部分结果，1、2、3、4   1，2-》5  5、3-》6 6、4-》7  折叠概念：list1、list2 -》list1 将list2添加到list1中并返回list1】
执行一个可选的最终转换，finisher函数
【将中间的累积类型转换为最终的结果类型，返回Function<T, R>类型，输入T参数，R返回结果】

Collectors有个Characteristics集合，提供hints暗示并发操作更好的性能，目的是告诉收集器对于目标执行一个怎样的一个操作。

使用collector做reduction(汇聚)串行的实现使用supplier函数创建单个结果容器，通过accumulator函数对每个输入元素做合并。
对于parallel并行实现对输入创建分区，对每个分区创建一个结果容器，然后通过combiner函数将多个子结果合并到一个新的结果当中。
【每个线程执行的逻辑跟单个线程执行逻辑相同，最终通过combiber函数merge】

为了确保串行和并行执行会生成等价的结果，所以，collector必须满足两个条件：identity(同一性)和associativity(结合性)
identity针对部分累积的结果，将中间部分结果与一个空的结果容器必须生成一个等价的结果（合并之前的累积结果）
也就是说对于部分累积的结果a，a==combiner.apply(a, supplier.get())
【上面的例子等价于 (list1, list2)-> {list1.addAll(list2); return list1}】

associativity是指分隔计算必须生成一个等价的结果，也就是说对于任何的输入元素t1和t2，得到的结果r1和r2是结果是等价的。
串行：
A a1 = supplier.get();
accumulator.accept(a1, t1); // 第一个参数每一次累积之后的中间结果，流中要处理的下一个元素, 处理完之后t1内容已放到了a1中了
accumulator.accept(a1, t2); // 将t2结果添加到a1
R r1 = finisher.apply(a1);  // a1容器生成新的结果r1
并行：
A a2 = supplier.get();
accumulator.accept(a1, t1); // t1元素放到a2
A a3 = supplier.get(); 
accumulator.accept(a3, t2); // t2元素翻到a3
R r2 = finisher.apply(combiner.apply(a2, a3)); // a2、a3生成新的结果r2

对于不包含unordered无序的集合结果来说，如果finisher.apply(a1).equals(finisher.apply(a2))相等，则累积的结果a1和a2是等价的。
对于无序的收集器来说，这种等价性就要求没那么严格了。

基于Collector实现的汇聚的库，比如说Stream的collect方法接受Collector的参数，必须遵守如下的约定：
【Collector.reducing方法， Stream.reduce 3个重载的方法  Reduce不可变的  Collect可变的】
1. 传递给accumulator的第一个参数以及传递给combiner的两个参数以及传递给finisher的参数必须是
supplier、accumulator、combiner上一次调用的中间结果。
2. 实现不应该对supplier、accumulator、combiner函数结果做任何事情。否则在并行情况下会错乱。
3. 如果结果传递给了combiner、finisher函数返回一个新的结果，以前的数据已使用过了不会在被使用了。
4. 一旦结果被传递给了combiner、funisher函数，它就不会再一次被传递给了accumulator函数(因为是中间操作)了。
5. 对于非并发的收集器来说，从supplier、accumulator、combiner返回的任何结果一定是针对当前唯一的线程的，使得collection用于并行情况，
而不需要Collector有额外的同步操作，reduction实现必须管理输入分区，分区处理是独立的，只有当accumulator完合并。
6. 对于并发，结果容器被多个线程同时调用，一个结果容器中执行完成。
7. 必须是无序的

收集器可以组合，示例：
Collector<Employee, ?, Integer> summingSalaries
          = Collectors.summingInt(Employee::getSalary))
Collector<Employee, ?, Map<Department, Integer>> summingSalariesByDept
          = Collectors.groupingBy(Employee::getDepartment, summingSalaries);
 
示例理解1：
合并结果到TreeSet集合中
    Collector<Widget, ?, TreeSet<Widget>> intoSet =
          Collector.of(TreeSet::new, TreeSet::add,
                      (left, right) -> { left.addAll(right); return left; });
                      
示例理解2：
执行汇聚结果操作
    R container = collector.supplier().get();
     for (T t : data)
         collector.accumulator().accept(container, t); // container是结果容器，t是每个输入元素
     return collector.finisher().apply(container); // 应用结果容器，返回最终的结果
                      
详解Collector参数：
Collector<T, A, R>
T：进行reduction操作的输入元素的类型，就是流中的每个元素的类型。
A：reduction操作的可变的累积类型，就是集合的结果类型，eg:ArrayList
R：汇聚之后的结果类型

Supplier<A> supplier函数：返回一个A类型的结果容器
BiConsumer<A, T> accumulator函数：第一个参数A类型的结果容器（作为下一次调用的参数），T：流中待处理的下一个元素的类型。
BinaryOperator<A> combiner函数：两个子A类型的结果，合并生成一个新的A类型的结果容器。
Function<A, R> finisher函数：输入A类型结果容器，最终返回汇聚之后R类型的结果。


#### 函数式编程最大的特点：表示做什么，而不是如何做。










