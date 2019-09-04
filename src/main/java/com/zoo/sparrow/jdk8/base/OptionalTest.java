package com.zoo.sparrow.jdk8.base;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Optional函数式接口方式的使用
 *
 * Created by David.Liu on 17/3/26.
 */
public class OptionalTest {
    public static void main(String[] args) {
        Optional<String> optional = Optional.ofNullable(null);

        // 错误的使用姿势
        //if (optional.isPresent()) {
        //    System.out.println(optional.get());
        //}

        // 推荐使用方式
        optional.ifPresent(item -> System.out.println(item));
        System.out.println("-------");
        System.out.println(optional.orElse("wrold"));
        System.out.println(optional.orElseGet(() -> "nihao"));
        System.out.println("--------");


        /*
         如果列表为空则返回空列表，如果有值返回结果；
         传统方式 if (list != null && list.size > 0) { return list;} else {return Collections.emptyList();}
         */
        Employee employee1 = new Employee();
        employee1.setName("zhangsan");

        Employee employee2 = new Employee();
        employee2.setName("lisi");

        Employee employee3 = new Employee();
        employee3.setName("wangwu");
        List<Employee> list = Arrays.asList(employee1, employee2, employee3);

        System.out.println("------公司对象中员工列表是空的");
        Company company = new Company();
        OptionalTest optionalTest = new OptionalTest();
        System.out.println(optionalTest.processEmployee(company));

        System.out.println("------公司对象中员工列表非空");
        company.setEmployees(list);
        System.out.println(optionalTest.processEmployee(company));
    }

    public List<Employee> processEmployee(Company company) {
        Optional<Company> optional1 = Optional.ofNullable(company);
        return optional1.map(c -> c.getEmployees()).orElseThrow(() -> new RuntimeException("空了")); //.orElse(Collections.emptyList());
    }

    private static class Employee {
        String name;

        int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

    }

    private static class Company {
        String name;

        List<Employee> employees;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }
    }
}
