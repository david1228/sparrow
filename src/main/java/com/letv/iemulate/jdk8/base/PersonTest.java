package com.letv.iemulate.jdk8.base;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by liudewei1228 on 17/3/22.
 */
public class PersonTest {

    public static void main(String[] args) {

        // 需求：查找年龄大于5岁的所有人

        Person p1 = new Person(2, "zhangsan");
        Person p2 = new Person(4, "lisi");
        Person p3 = new Person(6, "wangwu");
        Person p4 = new Person(9, "zhaoliu");

        List<Person> personList = Arrays.asList(p1, p2, p3, p4);

        PersonTest personTest = new PersonTest();
        // 查找集合中包括指定字母的人
        List<Person> result = personTest.getPersonsByUserName("z", personList);
        result.forEach(p -> {
            System.out.println(p.getName());
        });
        System.out.println("-----------");

        // 查询集合中大于指定年龄的
        personTest.getPersonsOverAge(4, personList).forEach((Person p) -> {
            System.out.println(p);
        });

        System.out.println("------------");
        personTest.getPersonsOverAge2(6, personList).forEach((Person p) -> {
            System.out.println(p);
        });

        System.out.println("------------");
        personTest.getPersonsOverAge2(2, personList).forEach((Person p) -> {
            System.out.println(p);
        });

    }

    public List<Person> getPersonsByUserName(String userName, List<Person> personList) {
        return personList.stream().filter(person -> person.getName().contains(userName)).collect(Collectors.toList());
    }

    public List<Person> getPersonsOverAge(int age, List<Person> personList) {
        // 定义一个BiFunction函数，接受两个参数，返回一个值R
        BiFunction<Integer, List<Person>, List<Person>> biFunction = new BiFunction<Integer, List<Person>, List<Person>>() {
            @Override
            public List<Person> apply(Integer integer, List<Person> personList) {
                return personList.stream().filter(p -> p.getAge() > age).collect(Collectors.toList());
            }
        };

        // 调用apply接口执行
        return biFunction.apply(age, personList);
    }

    // 精简下
    public List<Person> getPersonsOverAge2(int age, List<Person> personList) {
        // BiFunction接受两个参数，返回一个值R
        //BiFunction<Integer, List<Person>, List<Person>> biFunction = (Integer integer, List<Person> persons) -> {
        //    return persons.stream().filter(p -> p.getAge() > age).collect(Collectors.toList());
        //};
        BiFunction<Integer, List<Person>, List<Person>> function = (personAge, persons) ->
                persons.stream().filter(p -> p.getAge() > age).collect(Collectors.toList());

        return function.apply(age, personList);
    }


}
