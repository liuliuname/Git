package com.example.thred.sync;

public class RunnableSyncExaple implements Runnable {

    public void run() {
        synchronized (this){
            for(int i = 0; i < 10; i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "-->loop " + i);
            }
        }
    }

    public static void main(String[] args) {

        //此时synchronized  是同一个锁 也就是runnableSyncExaple对象  所以thred2执行时候需要等待thred1释放
        RunnableSyncExaple runnableSyncExaple = new RunnableSyncExaple();
        Thread thread1 = new Thread(runnableSyncExaple);
        Thread thread2 = new Thread(runnableSyncExaple);
        thread1.start();
        thread2.start();
    }
}
