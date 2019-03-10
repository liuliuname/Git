package com.example.threadPool;

import java.util.concurrent.*;

public class ThredPoolExaple {

    public static void main(String[] args) {
        ExecutorService executorService = (ExecutorService) Executors.newFixedThreadPool(2);
        MyThred thred1 = new MyThred("t1");
        MyThred thred2 = new MyThred("t2");
        MyThred thred3 = new MyThred("t3");
        MyThred thred4 = new MyThred("t4");
       /* 说明：execute()的作用是将任务添加到线程池中执行。它会分为3种情况进行处理：
        情况1 -- 如果"线程池中任务数量" < "核心池大小"时，即线程池中少于corePoolSize个任务；此时就新建一个线程，并将该任务添加到线程中进行执行。
        情况2 -- 如果"线程池中任务数量" >= "核心池大小"，并且"线程池是允许状态"；此时，则将任务添加到阻塞队列中阻塞等待。在该情况下，会再次确认"线程池的状态"，如果"第2次读到的线程池状态"和"第1次读到的线程池状态"不同，则从阻塞队列中删除该任务。
        情况3 -- 非以上两种情况。在这种情况下，尝试新建一个线程，并将该任务添加到线程中进行执行。如果执行失败，则通过reject()拒绝该任务
       */

        /*线程池共包括4种拒绝策略，它们分别是：AbortPolicy, CallerRunsPolicy, DiscardOldestPolicy和DiscardPolicy。

        AbortPolicy         -- 当任务添加到线程池中被拒绝时，它将抛出 RejectedExecutionException 异常。
        CallerRunsPolicy    -- 当任务添加到线程池中被拒绝时，会在线程池当前正在运行的Thread线程池中处理被拒绝的任务。
        DiscardOldestPolicy -- 当任务添加到线程池中被拒绝时，线程池会放弃等待队列中最旧的未处理任务，然后将被拒绝的任务添加到等待队列中。
        DiscardPolicy       -- 当任务添加到线程池中被拒绝时，线程池将丢弃被拒绝的任务。
        线程池默认的处理策略是AbortPolicy！*/
        executorService.execute(thred1);
        executorService.execute(thred2);
        executorService.execute(thred3);
        executorService.execute(thred4);
        executorService.shutdown();
    }


}

class MyThred extends Thread{
    String name ;
    public MyThred(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "执行");
    }
}
