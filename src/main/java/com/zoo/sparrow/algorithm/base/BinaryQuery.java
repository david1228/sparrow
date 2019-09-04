package com.zoo.sparrow.algorithm.base;

/**
 * @author liudewei
 * @date 2019/8/25
 */
public class BinaryQuery {


  public static void main(String[] args) {
    int[] array = new int[]{1, 4, 5, 8, 9, 7, 2, 3};

    System.out.println( biSearch(array, 3));
  }

  public static int biSearch(int[] array, int a) {
    int lo = 0;
    int hi = array.length - 1;
    int mid;
    while (lo <= hi) {
      mid = (lo + hi) / 2;//中间位置
      if (array[mid] == a) {
        return mid + 1;
      } else if (array[mid] < a) { //向右查找 lo=mid+1;
      } else { //向左查找 hi=mid-1;
      }
    }
    return -1;
  }
}
