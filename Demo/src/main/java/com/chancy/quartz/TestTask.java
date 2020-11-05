package com.chancy.quartz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

public class TestTask {
    public void say() {
        System.out.println("Myjob开始执行了。。。。" + new Date());

    }

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
}
