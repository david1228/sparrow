### 主要讲解流重要概念、方法、常用API
#### 流包括三部分：
    1. 源【数组、集合、IOchannel等】
    2. 0个或多个中间操作【多次返回Stream...】
    3. 终止操作【reduce sum等】
    
#### 流的分类：
    惰性求值
    及早求值
    
    构造一个Stream对象
    
    
#### 流
    * Collection提供了新的Stream()方法
    * 流不存储值， 通过管道的方式获取值
    * 本质是函数式的，对流的操作会生成一个结果，不过并不会修改底层数据源，集合可以作为流的底层数据源
    * 延迟查找，很多流操作（过滤、映射、排序等）都可以延迟实现
    * 参考文章：https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
    
    
#### 内部迭代和外部迭代区别剖析， StreamTest7.java
    * Stream类似于SQL的语句方式，是一种描述性极强的语言
    举个例子对比：
    SQL：select name from person where age > 20 and name = 'zhangsan' order by age desc;
    Stream: persons.stream().filter(p -> p.getAge() > 20 && name.equals("zhangsan")).sorted(...).forEach(p -> System.out.println(p.getName));
    * 集合关注的是数据与数据存储本身
    * 集合，开发者自行对集合通过迭代器遍历，做过滤等操作
    * 在JDK8之前对于集合的遍历都是属于外部迭代，而JDK8中的Stream方式称作内部迭代.
    举个例子说明：
    如上述stream方式对应的集合遍历伪代码：
    List<Person> list = new ArrayList<Person>();
    for (Person p : persons) {
        if (p.getAge() > 20 && p.getName.equals("zhangsan")) {
           list.add(p);
        }
    }
    // 排序
    Collections.sort(list, new Comparator(){
        ...
    })
    
    for (Person li : list) {
        System.out.printn(li.getName());
    }
    * 流关注的是对数据的计算，底层是通过fork join方式并行执行
      同时，底层会尽可能以最优方式执行，以上举例中注意多条指令底层实际是在一次循环中处理，并非多次循环过滤.
    * 流，开发者只需提供执行指令，最终交由Stream容器去执行这些指令
    * 流，所有中间操作都是返回一个Stream对象，如果返回Stream一定是个中间操作，并非终止操作 比如说Stream<Person>，Stream<String>， Stream<Integer>
    * 流，终止操作则不会返回Stream类型，可能不返回值，也可能返回其他类型的单个值，比如调用了count collect
    * 流与迭代器的不同：流是无法重复使用和消费的.
    