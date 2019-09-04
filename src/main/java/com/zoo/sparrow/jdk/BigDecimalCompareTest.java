package com.zoo.sparrow.jdk;

import java.math.BigDecimal;

/**
 * Created by David.Liu on 17/6/29.
 */
public class BigDecimalCompareTest {
    public static void main(String[] args) {
        BigDecimal totalRefund = new BigDecimal(10);
        BigDecimal totalPrice = new BigDecimal(10);
        System.out.println(totalRefund.compareTo(totalPrice));
    }

}
