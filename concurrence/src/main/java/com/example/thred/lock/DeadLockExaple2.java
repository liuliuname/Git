package com.example.thred.lock;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 模拟死锁
 *
 * 互斥条件 n比如syn(o1){} syn(o2){}
 * 请求和保持条件  保持o1请求 o2  /保持o2 请求o1
 * 不剥夺条件  o1 o2 都不会被别的线程剥夺
 * 环路等待条件 o1在等待o2  /o2在等待o1
 */
public class DeadLockExaple2 {


    public static void main(String[] args) {
        AA aa = new AA();
        BB bb = new BB(aa);
        aa.setBB(bb);

        new Thread(aa).start();
        new Thread(bb).start();

    }

}

class AA implements Runnable{
    private BB bb;

    public AA(){

    }

    public void setBB(BB bb){
        this.bb = bb;
    }



    @Override
    public void run() {
        synchronized (this){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("AA.run()");
            bb.bb();
        }
    }

    public synchronized void aa() {
        System.out.println("aa");
    }
}

class BB implements  Runnable{

    private AA aa;

    public BB(AA aa){
        this.aa = aa;
    }

    @Override
    public void run() {
        synchronized (this){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("BB.run()");
            aa.aa();
        }
    }


    public synchronized void bb() {
        System.out.println("bb");
    }
}
