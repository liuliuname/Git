package com.example.thred.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/***
 * 模仿ArrayBlockingQueue实现阻塞队列
 */
public class ArrayBlockingQueueExaple1 {


    //第一步 定义参数
    private final List list;//数据集合
    private final Integer limit;//队列大小

    //第二步  定义构造器用于参入参数
    public ArrayBlockingQueueExaple1(Integer limit) {
        this.limit = limit;
        list = new ArrayList();
    }

    //第三步 实现阻塞队列 需要用到锁 与 condition条件  ReentrantLock
    private final ReentrantLock lock = new ReentrantLock();
    //第四步  定义阻塞条件
    private final Condition lockEmpty = lock.newCondition();//数据为空条件
    private final Condition lockFull = lock.newCondition();//数据满条件

    //定义相关方法 put  take

    public void put(String value) {
       /* lock.lock();// 10   9个等待
        try {
            System.out.println("锁已经释放");
            lockFull.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }*/
        lock.lock();
        try {
            if (list.size() == limit) {
                //await当然会释放锁，调用await是为了能让其他线程访问竞争的资源，并等待在当前代码处，当其他线程处理好之后再唤醒，此时当前线程将会再次获得锁，
                // 如果你不在finally中调用lock.unlock()，其他线程将无法获得当前线程持有的锁，将会出现无限等待的情况
                lockFull.await();
            } else {
                list.add(value);
                System.out.println("数据不满 需要添加 值为" + value + "size=" + list.size());
            }
            lockEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void take() {
        lock.lock();
        try {
            if (list.size() == 0) {
                lockEmpty.await();
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String remove = (String) list.remove(0);
                System.out.println("删除数据 值为" + remove);
            }
            lockFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        final ThreadABQ abq = new ThreadABQ(new ArrayBlockingQueueExaple1(10));
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executor.execute(abq);
        }
        executor.shutdown();
    }

    static class ThreadABQ implements Runnable {
        ArrayBlockingQueueExaple1 arrayBlockingQueueExaple1;

        public ThreadABQ(ArrayBlockingQueueExaple1 arrayBlockingQueueExaple1) {
            this.arrayBlockingQueueExaple1 = arrayBlockingQueueExaple1;
        }

        @Override
        public void run() {
            Random random = new Random();
            int i = random.nextInt(1000);
            if (i > 300) {
                arrayBlockingQueueExaple1.put(i + "");
            } else {
                arrayBlockingQueueExaple1.take();
            }
        }

    }

}
