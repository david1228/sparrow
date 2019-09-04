package com.zoo.sparrow.joda;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Created by David.Liu on 17/5/3.
 */
public class JodaTest1 {

    public static void main(String[] args) {

        // 当天
        DateTime today = new DateTime();
        System.out.println("today:" + today.toString("yyyy-MM-dd"));

        // 明天
        DateTime tomorrow = today.plusDays(1);

        System.out.println("tomorrow:" + tomorrow.toString("yyyy-MM-dd"));

        DateTime day1 = today.withDayOfMonth(1);
        System.out.println("day1:" + day1.toString("yyyy-MM-dd"));

        LocalDate localDate = new LocalDate();
        System.out.println(localDate);
        System.out.println("------------");

        localDate = localDate.plusMonths(3).dayOfMonth().withMaximumValue();
        System.out.println("3个月后当月最后那一天：" + localDate);

        // 计算2年前第3个月最后1天的日期
        DateTime dateTime = new DateTime();
        DateTime dateTime1 = dateTime.minusYears(2).monthOfYear().setCopy(3).dayOfMonth().withMinimumValue();
        System.out.println("计算2年前第3个月最后1天的日期:" + dateTime1.toString("yyyy-MM-dd"));
    }



}
