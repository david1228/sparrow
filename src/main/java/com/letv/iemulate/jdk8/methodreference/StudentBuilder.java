package com.letv.iemulate.jdk8.methodreference;
import java.util.function.Function;

/**
 * Created by David.Liu on 17/3/27.
 */
public class StudentBuilder {

    /**
     * 无参数构造一个对象
     *
     * @param supplier
     * @return
     */
    public Student getStudent(java.util.function.Supplier<com.letv.iemulate.jdk8.methodreference.Student> supplier){
        return supplier.get();
    }

    /**
     * 输入两个参数返回一个构造一个对象
     *
     * @param name
     * @param score
     * @param biFunction
     * @return
     */
    public Student getStudent(String name, Integer score, java.util.function.BiFunction<String, Integer, Student> biFunction){
        return  biFunction.apply(name, score);
    }

}
