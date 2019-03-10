package com.example.thred.sync;

import static java.lang.Thread.sleep;

public class ThredSyncExaple extends Thread {

    public ThredSyncExaple(String name){
        super(name);
    }

    @Override
    public void run() {
        synchronized (this){
            for(int i = 0; i < 10; i++){
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(this.getName() + "-->loop " + i);
            }
        }
    }

    public static void main(String[] args) {
        //两个不同的对象 一个 syncExaple  另一个syncExaple1
        ThredSyncExaple syncExaple = new ThredSyncExaple("t1");
        ThredSyncExaple syncExaple1 = new ThredSyncExaple("t2");
        syncExaple.start();
        syncExaple1.start();
    }
}
