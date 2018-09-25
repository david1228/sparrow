package com.zoo.sparrow.jdk8.base;

/**
 * 声明函数式接口
 */
@FunctionalInterface
interface MyInterface {

    /**
     * 唯一的抽象方法
     */
    void test();

    static void staticTest(){
        System.out.println("static method");
    }

    default String staticString(){return null;}

    default void defaultTest() {
        System.out.println("default method");
    }

}

public class Test1 {

    public void myTest(MyInterface myInterface) {
        System.out.println("before myTest");
        myInterface.test();
        myInterface.defaultTest();
        MyInterface.staticTest();
        System.out.println("after myTest");
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();

        // normal
        test1.myTest(new MyInterface() {
            @Override
            public void test() {
                System.out.println("test1 = " + test1);
            }
        });

        // lambda
        test1.myTest(() -> {
            System.out.println("test1 lambda = " + test1);
        });

        MyInterface myInterface = () -> {
            System.out.println("myinterface lambda");
        };

        System.out.println(myInterface.getClass());
        System.out.println(myInterface.getClass().getSuperclass());
        System.out.println(myInterface.getClass().getInterfaces()[0]);
    }
}
