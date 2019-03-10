package com.example.thred.policy;


import java.util.concurrent.*;

public class DiscardPolicy {

    private static Integer THREADS_SIZE =10;

    private static Integer CAPACITY  =10;

    public static void main(String[] args) {

        // 创建线程池。线程池的"最大池大小"和"核心池大小"都为1(THREADS_SIZE)，"线程池"的阻塞队列容量为1(CAPACITY)。
        ThreadPoolExecutor pool = new ThreadPoolExecutor(THREADS_SIZE,CAPACITY,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(CAPACITY));
        //拒绝策略
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        long startTime = System.currentTimeMillis();
        //for(int i =0;i<10;i++){
            Runnable myThred = new MyThred();
            Runnable myThred2 = new MyThred1();
            Runnable myThred3 = new MyThred2();
            pool.execute(myThred);
            pool.execute(myThred2);
            pool.execute(myThred3);
        //}

        pool.shutdown();
        System.out.println(System.currentTimeMillis() - startTime);
      /*  long startTime = System.currentTimeMillis();
        method1();
        method2();
        method3();
        System.out.println(System.currentTimeMillis() - startTime);*/

    }

    public static void method1(){
        try {
            Thread.sleep(1000);
            System.out.println("method 1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void method2(){
        try {
            Thread.sleep(1000);
            System.out.println("method 2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void method3(){
        try {
            Thread.sleep(1000);
            System.out.println("method 3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

class MyThred implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method 1");
    }
}

class MyThred1 implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method 2");
    }
}


class MyThred2 implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("method 3");
    }
}