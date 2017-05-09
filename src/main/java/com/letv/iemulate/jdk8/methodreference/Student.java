package com.letv.iemulate.jdk8.methodreference;

/**
 * Created by David.Liu on 17/3/27.
 */
public class Student {

    private String name;
    private int score;

    public Student(){}

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override public String toString() {
        return "Student{" + "name='" + name + '\'' + ", score=" + score + '}';
    }

    // 非良好的设计，测试静态方法::使用
    public static int sortStudentsByScore(Student s1, Student s2) {
        return s1.getScore() - s2.getScore();
    }

    public static int sortStudentsByName(Student s1, Student s2) {
        return s1.getName().compareToIgnoreCase(s2.getName());
    }

    // 实例方法
    public int sortByScore(Student stu){
        return this.getScore() - stu.getScore();
    }

    // 实例方法
    public int sortByName(Student stu) {
        return this.getName().compareToIgnoreCase(stu.getName());
    }

}
