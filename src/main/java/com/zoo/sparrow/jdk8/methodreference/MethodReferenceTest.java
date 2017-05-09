package com.zoo.sparrow.jdk8.methodreference;

import java.util.List;

/**
 * 我们可以把方法引用看作是一个函数指针，function pointer
 * classname::staticmethod 类名::方法名，表示的是Lambda表达式的一种语法糖.是一种jdk8中新增的表达式.
 * classname.staticmethod(..) 类名.静态方法名，调用的方法名后面一定带着括号的.
 *
 * 方法引用共4种：
 * 1. 类名::静态方法名
 * 2. 引用明(对象名)::实例方法名
 * 3. 类名::实例方法名,
 * 深入理解下：等价于(Type t1, Type t2) -> t1.compareTo(t2);  Type::compareTo
 *           相当于第一个参数t1对象调用compareTo, 第二参数t2传入compareTo方法中实现排序.
 * 4. 构造方法引用: 类名::new
 *
 * Created by David.Liu on 17/3/26.
 */
public class MethodReferenceTest {

    public static void main(String[] args) {
        //1. 类名::静态方法名
        Student zs = new Student("zhangsan", 11);
        Student lisi = new Student("lisi", 12);
        Student ww = new Student("wangwu", 13);

        List<Student> list = java.util.Arrays.asList(lisi, ww, zs);

        System.out.println("-------lambda表达式传统方式");
//        list.sort((stu1, stu2) -> Student.sortStudentsByScore(stu1, stu2));

        System.out.println("---------1.类名::静态方法名， 按分数排序");
        list.sort(Student::sortStudentsByScore);
        list.forEach(p -> System.out.println(p));
        System.out.println("---------1.类名::静态方法名, 按名称排序");
        list.sort(Student::sortStudentsByName);
        list.forEach(p -> System.out.println(p));

        System.out.println("---------2.引用明(对象名)::实例方法名, 按分数排序");
        list.sort((stu1, stu2) -> stu1.sortByScore(stu2));
        list.forEach(p -> System.out.println(p));
        System.out.println("---------2.引用明(对象名)::实例方法名, 按名称排序");
        list.sort((stu1, stu2) -> stu1.sortByName(stu2));
        list.forEach(p -> System.out.println(p));

        System.out.println("---------2.引用明(对象名)::实例方法名 + 3. 类名::实例方法名");
        java.util.List<String> cities = java.util.Arrays.asList("bj", "ou", "hk");
        java.util.Collections.sort(cities, String::compareToIgnoreCase);
        cities.forEach(System.out::println);

        System.out.println("-------3. 类名::实例方法名, 按分数排序");
        list.sort(Student::sortByScore);
        list.forEach(p -> System.out.println(p));
        System.out.println("-------3. 类名::实例方法名, 按名称排序");
        list.sort(Student::sortByName);
        list.forEach(p -> System.out.println(p));

        System.out.println("-------4. 构造方法引用: 类名::new");
        StudentBuilder studentBuilder = new StudentBuilder();
        System.out.println(studentBuilder.getStudent(Student::new));
        System.out.println(studentBuilder.getStudent("zhaoliu", 18, Student::new));

    }




}
