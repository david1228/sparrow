package com.zoo.sparrow.algorithm;

/**
 * @author liudewei
 * @date 2019/3/29
 */
public class AjustMinHeap100 {

    public static int[] getTheMaxNums(int[] a) {
        int[] result = new int[100];
        for (int i = 0; i < 100; i++) {
            result[i] = a[i];
        }
        int len = result.length;
        for (int i = len / 2; i >= 0; i--) {
            adjustMinHeap(result, i, len);
        }
        for (int i = 100; i < a.length; i++) {
            if (a[i] > result[0]) {
                insert(a, a[i]);
            }
        }
        return result;
    }

    public static void insert(int[] result, int num) {
        result[0] = num;
        adjustMinHeap(result, 0, result.length);
    }

    public static void adjustMinHeap(int[] result, int pos, int len) {
        int temp;
        int child;
        //int len = result.length;
        for (temp = result[pos]; pos < len; pos = child) {
            child = 2 * pos + 1;
            if (result[child] > result[child + 1]) {
                child++;
            }
            if (temp > result[child]) {
                result[pos] = result[child];
            } else {
                break;
            }
        }
        result[pos] = temp;
    }

    public static void main(String[] args) {
        int[] aa;
        aa = new int[10000];
        for (int i = 0; i < 10000; i++) {
            aa[i] = i;
        }
        System.out.println(getTheMaxNums(aa));;
    }

}
