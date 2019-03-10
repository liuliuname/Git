package com.example.thred.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArrayBlockingQueueExaple {

    public static void main(String[] args) {
        final BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 1; i++) {
            exec.execute(new TestRunnable((ArrayBlockingQueue<Integer>) queue));
        }
        exec.shutdown();
    }

    static class TestRunnable implements Runnable{
        private final ArrayBlockingQueue<Integer> myBlocingQueue;

        TestRunnable(ArrayBlockingQueue<Integer> myBlocingQueue) {
            this.myBlocingQueue = myBlocingQueue;
        }

        @Override
        public void run() {
            while (true) {
                Random random = new Random();
                int r = random.nextInt(1000);//该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n。
                //生成随机数,按照一定比率读取或者放入，可以更改！！！
                //if (r < 300) {
                    try {
                        myBlocingQueue.put(r);
                        System.out.println("添加成功 r= " + r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
               // } else {
                    try {
                        //Thread.sleep(10000);
                        if(myBlocingQueue.size() == 10){
                            Thread.sleep(10000);
                            Integer take = myBlocingQueue.take();
                            System.out.println("扣减成功 take= " + take);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                //}
            }
        }
    }



}
