package com.chancy.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Test {
    @org.junit.Test
    public void test() {
        ArrayList<String> arrayList = new ArrayList<String>();

        ArrayList<Integer> arrayList1 = new ArrayList<>();

        System.out.println(arrayList.getClass().equals(arrayList1.getClass()));//true
    }

    @org.junit.Test
    public void test2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer

        list.getClass().getMethod("add", Object.class).invoke(list, "asd");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }



    @org.junit.Test
    public void name() {
        System.out.println((int) (Math.random() * 10000000));
        Integer i1 = 33;
        Integer i2 = 33;
        System.out.println(i1 == i2);// 输出true
        Integer i11 = 333;
        Integer i22 = 333;

        System.out.println(i11 == i22);// 输出false
        Double i3 = 1.2;
        Double i4 = 1.2;
        System.out.println(i3 == i4);// 输出false

        //LinkedList
    }
}
