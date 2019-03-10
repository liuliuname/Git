package com.example.thred;

import java.util.ArrayList;

/**
 * 存在线程安全问题
 */
public class ProgressBarExaple {

    public static Boolean falg = false;

    public static ArrayList<Thread> threads = new ArrayList<Thread>();

    public static Integer count = 0;

    public static Integer getCount() {
        return count;
    }

    public static void setCount(Integer count) {
        ProgressBarExaple.count = count;
    }

    public static void main(String[] args) {
        final ProgressBarExaple progressBarExaple = new ProgressBarExaple();

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
        threads.add(addThred);
        threads.add(addThred1);
        threads.add(addThred2);

        addThred.start();
        addThred1.start();
        addThred2.start();
        getThred.start();

    }

    public void addBarCount(ProgressBarExaple progressBarExaple){
        //处理业务逻辑 查看进度
       // synchronized (progressBarExaple){
            System.out.println("计算方法开始....");
            for(int i = 0; i < 100;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressBarExaple.setCount(count += i);
            }
            falg = true;
            System.out.println("计算方法结束....");
        //}
    }

    public Integer getBarCount(ProgressBarExaple progressBarExaple){
        //处理业务逻辑 查看进度
       while(true){
           try {
               Thread.sleep(1000);
               System.out.println("当前进度 为" +  progressBarExaple.getCount());
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           if(progressBarExaple.isThred(progressBarExaple.threads)){
               break;
           }
       }
       return count;
    }

    public static boolean isThred(ArrayList<Thread> threads){
        int size = threads.size();
        int i = 0;
        for(Thread thread:threads){
            boolean alive = thread.isAlive();
            if(!alive){
                i++;
            }
        }
        System.out.println("i=" + i + "size" +size);
        return size == i;
    }
}
