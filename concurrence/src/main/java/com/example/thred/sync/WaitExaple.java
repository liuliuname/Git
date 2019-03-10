package com.example.thred.sync;

public class WaitExaple extends Thread{

    public WaitExaple(String name){
        super(name);
    }

    @Override
    public void run() {
        //synchronized (this) {
            System.out.println(Thread.currentThread().getName()+" call notify()");
            // 唤醒当前的wait线程
           // notify();
        //}
    }

    public static void main(String[] args) {
        WaitExaple waitExaple = new WaitExaple("waitExaple");
       // synchronized (waitExaple){
            try {
                // 启动“线程t1”
                System.out.println(Thread.currentThread().getName()+" start waitExaple");
                waitExaple.start();

                // 主线程等待t1通过notify()唤醒。
                System.out.println(Thread.currentThread().getName()+" wait()");
                //说wait()的作用是让“当前线程”等待，而“当前线程”是指正在cpu上运行的线程！
                waitExaple.wait(3000);

                System.out.println(Thread.currentThread().getName()+" continue");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        //}
    }
}
