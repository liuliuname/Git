package com.example.thred.sync;

/**
 * 多线程同时调用方法 如果方法非synchronized 将不会被阻塞
 *
 */
public class ThredSyncExaple1{

    public void sync(){
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

    public void noSync(){
       // synchronized (this){
        for(int i = 0; i < 10; i++){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "-->loop " + i);
        }
       // }
    }


    public static void main(String[] args) {
        final ThredSyncExaple1 thredSyncExaple1 = new ThredSyncExaple1();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                thredSyncExaple1.sync();
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                thredSyncExaple1.noSync();
            }
        });

        thread.start();
        thread1.start();
    }
}
