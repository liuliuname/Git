package com.example.thred;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 需要知道一个任务的执行进度，比如我们常看到的进度条，实现方式可以是在任务中加入一个整型属性变量(这样不同方法可以共享)，
 * 任务执行一定程度就给变量值加1，另外开一个线程按时间间隔不断去访问这个变量，并反馈给用户。
 */
public class ThreadCount1 {

    private static AtomicInteger count = new AtomicInteger(0);

    public static Integer Icount = 0;

    public static Integer getIcount() {
        return Icount;
    }

    public static void setIcount(Integer icount) {
        Icount = icount;
    }

    public static void main(String[] args) {
        //Integer Icount = 0;
        Thread addThred = new Thread(new addCount());
        Thread getThred = new Thread(new getCount(Icount));
        addThred.start();
        getThred.start();
    }
}

class addCount implements  Runnable{
//    public Integer paramCount;
//    public addCount(Integer paramCount){
//        this.paramCount = paramCount;
//    }
    ThreadCount1 threadCount1 = new ThreadCount1();
    @Override
    public void run() {
        System.out.println("执行方法操作 count= " );
        for(int i = 0 ; i< 100; i++){
            try {
                Thread.sleep(10);
               // paramCount.addAndGet(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行方法操作结束");
    }
}

class getCount implements  Runnable{
    public Integer paramCount;
    public getCount(Integer paramCount){
        this.paramCount = paramCount;
    }

    @Override
    public void run() {
       while(true){
           try {
               Thread.sleep(100);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           System.out.println("count值为" + paramCount);
       }
    }
}