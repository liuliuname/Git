package com.example.thred;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgressBarExaple1 {

    public static Boolean falg = false;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        final ProgressBarExaple1 progressBarExaple = new ProgressBarExaple1();
        Thread addThred = new Thread(new Runnable() {
            @Override
            public void run() {
                progressBarExaple.addBarCount(progressBarExaple);
            }
        });

        Thread addThred1 = new Thread(new Runnable() {
            @Override
            public void run() {
                progressBarExaple.addBarCount(progressBarExaple);
            }
        });

        Thread addThred2 = new Thread(new Runnable() {
            @Override
            public void run() {
                progressBarExaple.addBarCount(progressBarExaple);
            }
        });

        Thread getThred = new Thread(new Runnable() {
            @Override
            public void run() {
                progressBarExaple.getBarCount(progressBarExaple);
            }
        });

        addThred.start();
        addThred1.start();
        addThred2.start();
        getThred.start();
    }

    public void addBarCount(ProgressBarExaple1 progressBarExaple){
        //处理业务逻辑 查看进度
        System.out.println("计算方法开始....");
        for(int i = 0; i < 100;i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AtomicInteger count = progressBarExaple.count;
            count.addAndGet(i);
        }
        falg = true;
        System.out.println("计算方法结束....");
    }

    public Integer getBarCount(ProgressBarExaple1 progressBarExaple){
        //处理业务逻辑 查看进度
       while(true){
           try {
               Thread.sleep(1000);
               System.out.println("当前进度 为" +  progressBarExaple.count.get());
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           if(falg){
               break;
           }
       }
       return progressBarExaple.count.get();
    }


}
