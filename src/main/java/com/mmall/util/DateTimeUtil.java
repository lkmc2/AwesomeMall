package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by lkmc2 on 2018/2/10.
 * 日期时间工具类
 */

public class DateTimeUtil {

    //joda-time
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss"; //标准格式

    /**
     * 将字符串转换成特定格式的日期
     * @param dateTimeStr 日期字符串
     * @param formatStr 日期格式
     * @return 带特定格式的日期
     */
    public static Date strToDate(String dateTimeStr, String formatStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(formatStr); //获取日期格式匹配器
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr); //将字符串转换成日期
        return dateTime.toDate(); //转换成java.util格式的日期
    }

    /**
     * 将日期转换成字符串
     * @param date 日期
     * @param formatStr 日期格式
     * @return 特定格式的日期字符串
     */
    public static String dateToStr(Date date, String formatStr) {
        if (date == null) {
            return StringUtils.EMPTY; //返回空字符串
        }
        DateTime dateTime = new DateTime(date); //生成joda-time的日期对象
        return dateTime.toString(formatStr); //返回指定格式的日期字符串
    }

    /**
     * 将字符串转换成默认格式的日期
     * @param dateTimeStr 日期字符串
     * @return 带默认格式的日期
     */
    public static Date strToDate(String dateTimeStr) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(STANDARD_FORMAT); //获取日期格式匹配器
        DateTime dateTime = dateTimeFormatter.parseDateTime(dateTimeStr); //将字符串转换成日期
        return dateTime.toDate(); //转换成java.util格式的日期
    }

    /**
     * 将日期转换成字符串
     * @param date 日期
     * @return 特定默认日期格式字符串
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return StringUtils.EMPTY; //返回空字符串
        }
        DateTime dateTime = new DateTime(date); //生成joda-time的日期对象
        return dateTime.toString(STANDARD_FORMAT); //返回指定格式的日期字符串
    }

    public static void main(String[] args) {
        System.out.println(DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        System.out.println(DateTimeUtil.strToDate("2010-01-01", "yyyy-MM-dd"));
    }
}
