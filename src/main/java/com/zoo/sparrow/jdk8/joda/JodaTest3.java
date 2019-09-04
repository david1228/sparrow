package com.zoo.sparrow.jdk8.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * Created by David.Liu on 17/5/3.
 */
public class JodaTest3 {


    // 标准UTC时间，客户端与服务端之间交互的时间最好都是UTC标准时间，客户端拿到UTC时间转换为本地区时间
    public static Date convertUTC2Date(String utcDate) {
        DateTime dateTime = DateTime.parse(utcDate, DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        return dateTime.toDate();
    }

    // 将Date类型转换为UTC时间
    public static String convertDate2UTC(Date javaDate){
      DateTime dateTime = new DateTime(javaDate, DateTimeZone.UTC);
        return dateTime.toString();
    }

    // 格式化Date为本地时间
    public static String convertDate2LocalByDateFormat(Date javaDate, String dateFormat) {
        DateTime dateTime = new DateTime(javaDate);
        return dateTime.toString(dateFormat);
    }

    public static void main(String[] args) {
        System.out.println(JodaTest3.convertUTC2Date("2014-11-04T09:22:54.876Z"));
        System.out.println(JodaTest3.convertDate2UTC(new Date()));
        System.out.println(JodaTest3.convertDate2LocalByDateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));

     }



}
