package com.chancy.quartz;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RespectDemo {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TestC testC = new TestC();
        Class<TestC> testCClass = TestC.class;
        String lkey = "{say}";

       /* Method say = testCClass.getDeclaredMethod("get"+lkey.toUpperCase().substring(1, 2) + lkey.substring(2));
        say.invoke(testC);

        Method say222 = testCClass.getDeclaredMethod("sayAAA");
        say222.invoke(testC);*/
        String time = "000044a3cf90\\s=9\\c=1\\j=3\\p=1";
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(time.substring(0, time.indexOf("\\")));

    }

    static class TestC {
        int a;
        public void getSay(){
            System.out.println("test");
        }

        public void sayAAA(){
            System.out.println("test3434");
        }
    }
}
