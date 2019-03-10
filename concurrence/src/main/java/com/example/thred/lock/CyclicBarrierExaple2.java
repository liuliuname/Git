package com.example.thred.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * CycliBarriar和CountdownLatch有什么区别
 *
 * 这两个的区别是CyclicBarrier可以重复使用已经通过的障碍，而CountdownLatch不能重复使用
 *
 */
public class CyclicBarrierExaple2 {
    private final static int THREAD_NUM = 10;
    public static void main(String[] args) {

        CyclicBarrier lock = new CyclicBarrier(THREAD_NUM, new Runnable() {
            @Override public void run() {
                System.out.println("大家都准备完成了");
            }
        });
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < THREAD_NUM; i++) {
            exec.submit(new CountdownLatchTask(lock, "Thread-"+i));
        }
        exec.shutdown();
       /* while(true){
            if(exec.isTerminated()){
                System.out.println("执行完成");
                break;
            }
        }*/
    }

    static class CountdownLatchTask implements Runnable{
        private final CyclicBarrier lock;
        private final String threadName;
        CountdownLatchTask(CyclicBarrier lock, String threadName) {
            this.lock = lock;
            this.threadName = threadName;
        }
        @Override public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(threadName + " 准备完成");
                try {
                    Thread.sleep(1000);
                    lock.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(threadName + " 执行完成");
            }

        }
    }

}
