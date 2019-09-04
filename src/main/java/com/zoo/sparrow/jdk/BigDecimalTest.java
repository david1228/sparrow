package com.zoo.sparrow.jdk;

import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by David.Liu on 17/6/27.
 */
public class BigDecimalTest {

    public static void main(String[] args) {
        //进位处理，2.35变成2.4  setScale(1,BigDecimal.ROUND_UP)； 四舍五入，2.35变成2.4  setScale(1, BigDecimal.ROUND_HALF_UP)
        System.out.println(new BigDecimal(2.31).setScale(1, BigDecimal.ROUND_UP)); // 总是进位并保留小数点后一位。
        System.out.println(new BigDecimal(2.35).setScale(1, BigDecimal.ROUND_HALF_UP)); // 四舍五入


        // 四舍五入，2.35变成2.3，如果是5则向下舍setScaler(1,BigDecimal.ROUND_HALF_DOWN)
        System.out.println(new BigDecimal(2.36).setScale(1, BigDecimal.ROUND_DOWN)); // 保留小数点后一位，不会四舍五入
        System.out.println(new BigDecimal(2.35).setScale(1, BigDecimal.ROUND_HALF_DOWN)); // 保留小数点后，四舍五入

        //scale指的是你小数点后的位数,roundingMode是小数的保留模式。它们都是BigDecimal中的常量字段
        BigDecimal b = new BigDecimal("123.456");
        System.out.println(b.scale()); // 3

        // divide(BigDecimal divisor, int scale, introundingMode)的意思是说：
        // 我用一个BigDecimal对象除以divisor后的结果，并且要求这个结果保留有scale个小数位，roundingMode表示的就是保留模式是什么，是四舍五入啊还是其它的.

        // 可以通过BigDecimal的compareTo方法来进行比较。 返回的结果是int类型，-1表示小于，0是等于，1是大于。

        // 在《Effective Java》这本书中也提到这个原则，float和double只能用来做科学计算或者是工程计算，在商业计算中我们要用java.math.BigDecimal。

        // 格式化
        System.out.println(formatValue(new BigDecimal(2.355232)));
    }

    // 格式化
    public static String formatValue(Object value) {
        String content = null;
        if (null == value) {
            content = "";
        } else {
            if (value instanceof BigDecimal) {
                // convert to String
                NumberFormat nf = NumberFormat.getInstance();
                nf.setMinimumFractionDigits(2);
                nf.setMaximumFractionDigits(2);
                content = nf.format(value);
            } else {
                content = String.valueOf(value);
            }
        }

        return content;
    }
}
