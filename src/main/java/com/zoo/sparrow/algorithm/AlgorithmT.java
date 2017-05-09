package com.zoo.sparrow.algorithm;

import org.junit.Test;

/**
 * Created by liudewei1228 on 17/3/8.
 */
public class AlgorithmT {

    @Test
    public void test() {
        int[] num = {1, 2, 2, 3, 4, 5, 6, 7, 8, 9};
        int sum = 7;
        findSum(num, sum);
    }

    public void findSum(int[] num, int sum) {
        int left = 0;
        int right = 0;

        for (int i = 0; i < num.length; i++) {
            int curSum = 0;
            left = i;

            right = i;
            while (curSum < sum) {
                curSum += num[right++];
            }
            if (curSum == sum) {
                System.out.println("left:"+i);
                for (int j = left; j < right; j++) {
                    System.out.print(num[j] + " ");
                }
                System.out.println();
            }
        }
    }
}

