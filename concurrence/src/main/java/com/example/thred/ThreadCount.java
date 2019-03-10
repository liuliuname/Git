package com.example.thred;

public class ThreadCount {
    public static void main(String[] args) {
        Thread[] threads=new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            threads[i]=new AThread();
            threads[i].start();
        }
    }
}

class AThread extends Thread{
    @Override
    public void run() {
        // TODO Auto-generated method stub
        @SuppressWarnings("unused")
        Counter counter=new Counter();
        System.out.println(Counter.calNum());
    }
}

class Counter{
    private static long num;
    public Counter(){
        synchronized (Counter.class) {
            num++;
        }
    }
    public static synchronized long calNum(){
        return num;
    }
}
