package com.letv.iemulate.jdk8.base;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Predicate函数式接口默认方法的使用
 * negate：取反 、 and：逻辑与 、 or：逻辑或
 *
 * Created by David.Liu on 17/3/24.
 */
public class PredicateMethodTest {
    public static void main(String[] args) {
        Person p1 = new Person(2, "zhangsan");
        Person p2 = new Person(4, "lisi");
        Person p3 = new Person(6, "wangwu");
        Person p4 = new Person(9, "zhaoliu");
        Person p5 = new Person(10, "zhangba");

        List<Person> persons = Arrays.asList(p1, p2, p3, p4, p5);

        PredicateMethodTest predicateTest2 = new PredicateMethodTest();
        predicateTest2.execAndInPredicate(persons, p -> p.getAge() > 4, p -> p.getName().endsWith("u"));
        System.out.println("-------");
        predicateTest2.execAndOrInPredicate(persons, p -> p.getAge() > 2, p -> p.getName().contains("a"), p -> p.getName().endsWith("i"));
        System.out.println("-------");
        predicateTest2.execAndOrNegateInPredicate(persons, p -> p.getAge() > 2, p -> p.getName().contains("a"), p -> p.getName().endsWith("i"));
    }


    public void execAndInPredicate(List<Person> persons, Predicate<Person> p1, Predicate<Person> p2) {
        persons.forEach(person -> {if (p1.and(p2).test(person)) {
            System.out.println("and方法使用：" + person);
        }});
    }

    public void execAndOrInPredicate(List<Person> persons, Predicate<Person> p1, Predicate<Person> p2, Predicate<Person> p3) {
        persons.forEach((Person person) -> {
            if (p1.and(p2).or(p3).test(person)) {
                System.out.println("and、or方法使用" + person);
            }
            ;
        });
    }

    public void execAndOrNegateInPredicate(List<Person> persons, Predicate<Person> p1, Predicate<Person> p2, Predicate<Person> p3) {
        persons.forEach(new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                if (p1.and(p2).or(p3).negate().test(person)) {
                    System.out.println("and、or、negate方法使用：" + person);
                }
            }
        });
    }
}
