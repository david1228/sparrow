package com.zoo.sparrow.jdk8.other;

import java.util.Arrays;
import java.util.List;

/**
 * Created by David.Liu on 17/8/14.
 */
public class PeekTest {

    public static void main(String[] args) {
        List<Debugging.Point> list = Arrays.asList(new Debugging.Point(12, 2), new Debugging.Point(3,2));

        list.stream().map(Debugging.Point::getX).peek(p -> System.out.println("---X: " + p)).filter(p -> p > 1).forEach(p -> System.out.println("结果：" + p));
    }


    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
