package com.zoo.sparrow.jdk8.other;

import java.util.Arrays;
import java.util.List;

/**
 * Created by David.Liu on 17/8/14.
 */
public class Debugging {
    public static void main(String[] args) {
        List<Point> list = Arrays.asList(new Point(12, 2), null);

        list.stream().map(Point::getX).filter(p -> p > 1).forEach(System.out::print);
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
