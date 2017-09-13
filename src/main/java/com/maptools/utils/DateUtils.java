package com.maptools.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * Created by mr on 2016/10/10.
 */
public class DateUtils {

    /**
     * 获得当前时间
     *
     * @return 当前时间字符串
     */
    public static String getTime() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return time;
    }

    /**
     * 对时间进行格式化
     *
     * @param formatStr 要进行格式化的时间
     * @return 返回格式化结果
     */
    public static String getTimeByStr(Date formatStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = "";
        try {
            time = format.format(formatStr);
        } catch (Exception e) {
            time = "";
        }
        return time;
    }

    /**
     * 对时间进行格式化
     *
     * @param formatStr 要进行格式化的时间
     * @return 返回格式化结果
     */
    public static String getTimeByStr(Timestamp formatStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = "";
        try {
            time = format.format(formatStr);
        } catch (Exception e) {
            time = "";
        }
        return time;
    }

    public static String getDateStr(Date str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = "";
        try {
            time = format.format(str);
        } catch (Exception e) {
            time = "";
        }
        return time;
    }

    /**
     * 用时间生成ID
     * @return
     */
    public static String createID() {
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        return time;
    }

    /**
     * 获取当前日期
     *
     * @return 当前日期
     */
    public static String getDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        return date;
    }


    public static String getDateBefore(String nowDate, int day) {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(nowDate);
        } catch (Exception e) {

        }
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        String strDate = sdf.format(now.getTime());
        return strDate;
    }

    /**
     * 获取开始时间
     * @param startDate 时间字符串
     * @return 开始时间
     */
    public static Date getStartDate(String startDate) {
        Date sdate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdate = sdf.parse(startDate + " 00:00:00");
        } catch (Exception ex) {
            sdate = new Date();
        }
        return sdate;
    }

    /**
     * 获取结束时间
     * @param endDate 结束时间字符串
     * @return 结束时间
     */
    public static Date getEndDate(String endDate) {
        Date edate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            edate = sdf.parse(endDate + " 23:59:59");
        } catch (Exception ex) {
            edate = new Date();
        }
        return edate;
    }

    /**
     * 获取开始时间
     * @param startDate 时间字符串
     * @return 开始时间
     */
    public static String getStartDateStr(String startDate) {
        return startDate + " 00:00:00";
    }

    /**
     * 获取结束时间
     * @param endDate 结束时间字符串
     * @return 结束时间
     */
    public static String getEndDateStr(String endDate) {
        return endDate + " 23:59:59";
    }
}
