package com.chancy.quartz;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicInfoServiceImpl {

    public static void queryByThread() {

        // 数据集合大小，由调用者自己指定
        int listSize = 100;

        // 开启的线程数
        int runSize = 20;

        // 一个线程处理数据条数，如果库中有100条数据，开启20个线程，那么每一个线程执行的条数就是5条
        int count = listSize / runSize;//5

        // 创建一个线程池，数量和开启线程的数量一样

        ExecutorService executor = Executors.newFixedThreadPool(runSize);
        // 计算sql语句中每个分页查询的起始和结束数据下标

        // 循环创建线程

        //此处调用具体的查询方法
        System.out.println("开始查询");
        final ArrayList<Object> arrayList = new ArrayList<Object>();
        for (int i = 0; i < runSize; i++) {
            final int index = i * count;
            final int num = count;
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    try {

                        //查询的结果如何保存下来，会不会存在覆盖的问题

                        System.out.println("每次查询的下标:" + index + ",条数:" + num);

                        //List<MusicInfo> list = musicInfoMapper.queryByThread(index, num);

                        //这里做成写入文件的方法
                        arrayList.add(index);
                    } catch (Exception e) {

                        System.out.println("查询失败" + e);

                    }

                }

            });

        }

        // 执行完关闭线程池
        System.out.println(arrayList.toArray());
        executor.shutdown();

    }

}

