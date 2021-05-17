package com.chancy.quartz;


import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RespectDemo {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ParseException {
        TestC testC = new TestC();
        Class<TestC> testCClass = TestC.class;
        String lkey = "{say}";

       /* Method say = testCClass.getDeclaredMethod("get"+lkey.toUpperCase().substring(1, 2) + lkey.substring(2));
        say.invoke(testC);

        Method say222 = testCClass.getDeclaredMethod("sayAAA");
        say222.invoke(testC);*/
        String time = "2020-11-09T13:39:37.00+08:00";
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(strDateFormat);



        System.out.println("----------------------11-------------------------");
        String s = DateUtils.utc2Local(time, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
        System.out.println(s);

        System.out.println("---------------------22--------------------------");
        Date date2 = localToUTC(time);
        System.out.println(date2.toString());
        System.out.println(simpleDateFormat.format(date2));

        System.out.println("---------------------33--------------------------");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dates = df.parse(time);
        System.out.println(dates.toString());
        SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date date1 = df1.parse("Wed Nov 11 06:01:35 UTC 2020");
        String strDateFormat1 = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(strDateFormat);
        System.out.println(simpleDateFormat1.format(date1));
    }

    static class TestC {
        int a;

        public void getSay() {
            System.out.println("test");
        }

        public void sayAAA() {
            System.out.println("test3434");
        }
    }

    /**
     * <p>Description: 本地时间转化为UTC时间</p>
     *
     * @param localTime
     * @return
     * @author wgs
     * @date 2018年10月19日 下午2:23:43
     */
    public static Date localToUTC(String localTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date localDate = null;
        try {
            localDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long localTimeInMillis = localDate.getTime();
        /** long时间转换成Calendar */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(localTimeInMillis);
        /** 取得时间偏移量 */
        int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);
        /** 取得夏令时差 */
        int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);
        /** 从本地时间里扣除这些差量，即可以取得UTC时间*/
        calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        /** 取得的时间就是UTC标准时间 */
        Date utcDate = new Date(calendar.getTimeInMillis());
        return utcDate;
    }

    /**
     * <p>Description:UTC时间转化为本地时间 </p>
     *
     * @param utcTime
     * @return
     * @author wgs
     * @date 2018年10月19日 下午2:23:24
     */
    public static Date utcToLocal(String utcTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date utcDate = null;
        try {
            utcDate = sdf.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.setTimeZone(TimeZone.getDefault());
        Date locatlDate = null;
        String localTime = sdf.format(utcDate.getTime());
        try {
            locatlDate = sdf.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return locatlDate;
    }
}
