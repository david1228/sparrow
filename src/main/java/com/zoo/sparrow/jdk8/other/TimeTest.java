package com.zoo.sparrow.jdk8.other;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Created by David.Liu on 17/8/10.
 */
public class TimeTest {

    public static void main(String[] args) {

        System.out.println(new Date());

        System.out.println("-------------");
        LocalDate localDate = LocalDate.of(2017, 8, 10);
        System.out.println(localDate.getYear());
        System.out.println(localDate.getMonthValue());
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.getDayOfYear());
        System.out.println(localDate.isLeapYear()); // 是否是闰年

        System.out.println(LocalDate.now());

        System.out.println("----------clock/zone");
        Clock clock = Clock.systemUTC();
        ZoneId zone = ZoneId.systemDefault();
        ZoneId customZone = ZoneId.of("Europe/Berlin");
        Clock customClock = Clock.system(customZone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.now();
        String formattedDate = date.format(formatter);
        System.out.println(formattedDate);

        System.out.println("------------");

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        System.out.println(localTime.getHour());
        System.out.println(localTime.getMinute());
        System.out.println(localTime.getSecond());

        System.out.println("-------------字符串直接转换为localDate");
        LocalDate localDate1 = LocalDate.parse("2017-08-08");
        LocalTime localTime1 = LocalTime.parse("13:45:20");
        System.out.println(localDate1);
        System.out.println(localTime1);
        // 使用DateTimeFormatter 代替java.util.DateFormat

        System.out.println("---------复合时间");
        LocalDateTime localDateTime = LocalDateTime.now(); // 标准的UTC时间格式
        System.out.println(localDateTime);
        System.out.println("提取localDate: " + localDateTime.toLocalDate());
        System.out.println("提取localTime: " + localDateTime.toLocalTime());

        System.out.println("---------instant");
        System.out.println(Instant.ofEpochSecond(2));
        System.out.println(Instant.now());
        Instant instant = Instant.now();
        LocalDateTime timeFromInstant = LocalDateTime.ofInstant(instant, zone);
        System.out.println(timeFromInstant);

        System.out.println("---------------纳秒、秒之差");
        LocalDateTime localDateTime1 = LocalDateTime.now();
        LocalDateTime localDateTime3 = LocalDateTime.of(2017, 8, 10, 18, 30, 10);
        Duration duration = Duration.between(localDateTime3, localDateTime1);
        System.out.println(duration.toMinutes());
        System.out.println(duration.toMillis());

        LocalDate localDate2 = LocalDate.of(2017,8,10);
        LocalDate localDate3 = LocalDate.of(2017, 8, 7);
        Period day = Period.between(localDate3, localDate2);
        System.out.println("perid相差："+day.getDays());
        System.out.println(localDate3.isAfter(localDate2));
        System.out.println(localDate3.isBefore(localDate2));
        System.out.println(localDate2.isEqual(localDate3));

        System.out.println("------------可修改版");
        LocalDate localDate4 = LocalDate.now();
        System.out.println(localDate4.withYear(2010));
        System.out.println(localDate4.withDayOfMonth(5));
        System.out.println(localDate4.withMonth(12).withYear(2012).withDayOfMonth(12));
        System.out.println(localDate4.with(ChronoField.MONTH_OF_YEAR, 9));
        System.out.println(localDate4);

        System.out.println("-----------plus、minus");
        LocalDate localDate5 = LocalDate.now();
        System.out.println(localDate5.minusMonths(2).plusDays(10).plusWeeks(2).plus(3, ChronoUnit.MONTHS));

        System.out.println("----------TemporalAdjuster");
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date2);
        System.out.println(date1.with(TemporalAdjusters.lastDayOfMonth()));

        System.out.println("---------formatter");
        LocalDate date3 = LocalDate.of(2014, 3, 18);
        String s1 = date3.format(DateTimeFormatter.BASIC_ISO_DATE);
        String s2 = date3.format(DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println(s1 + "   " + s2);

        LocalDateTime localDateTime2 = LocalDateTime.now();
        String s3 = localDateTime2.format(DateTimeFormatter.ISO_DATE);
        System.out.println(s3);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(localDateTime2.format(dateTimeFormatter));

        System.out.println("-------zone");
        ZoneId zoneId =  ZoneId.of("Asia/Shanghai");
        ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDateTime.now(), zone);
        System.out.println(zonedDateTime.getZone());
        System.out.println(zonedDateTime.format(dateTimeFormatter));
    }
}
