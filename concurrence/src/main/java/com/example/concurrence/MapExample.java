package com.example.concurrence;


import java.util.concurrent.*;

public class MapExample {

    private static int thredNum = 1;
    private static int clientNum = 5000;

    private static long count = 0;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(thredNum);
        for(int i =0 ; i < clientNum; i++){
           final int thredNum = i;
            executorService.execute(new Runnable() {
                public void run() {
                    try {
                        semaphore.acquire();
                        add();
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
        System.out.println(count);
    }

    private synchronized  static void add() {
        count++;
    }



}
