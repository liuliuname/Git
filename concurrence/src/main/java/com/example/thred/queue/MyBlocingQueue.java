package com.example.thred.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 用Java实现阻塞队列。
 *
 * 使用wait notify 实现
 *
 * 第一步 判断是否需要put 如果不需要wait 并且唤醒其它线程工作 如果需要将数据加入集合当中  集合为资源共享变量 一般通过创建线程构造器完成创建
 *
 * 第二步 take同理
 *
 * 第三步 测试 构建线程池 创建线程时将资源共享文件传入
 *
 */
public class MyBlocingQueue<E> {

    private final List list;
    private final int limit;//有大小限制的

    public MyBlocingQueue(int limit) {
        this.limit = limit;
        this.list = new LinkedList<E>();
    }

    //这是用synchronized写的,在list空或者满的时候效率会低，因为会一直轮询
    public void put1(E e){
        while(true){
            synchronized (list){
                if (list.size() < limit) {
                    System.out.println("list : " + list.toString());
                    System.out.println("put : " + e);
                    list.add(e);
                    return;
                }
            }
        }
    }
    public E take1(){
        while (true) {
            synchronized (list) {
                if (list.size() > 0){
                    System.out.println("list : " + list.toString());
                    E remove = (E) list.remove(0);
                    System.out.println("take : " + remove);
                    return remove;
                }
            }
        }
    }

    //用wait，notify写的,在list空或者满的时候效率会高一点，因为wait释放锁，然后等待唤醒
    public synchronized void put(E e){
        while (list.size() == limit){
            try {
                wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        System.out.println("list : " + list.toString());
        System.out.println("put : " + e);
        list.add(e);
        notifyAll();
    }
    public synchronized E take() {
        while (list.size() == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("list : " + list.toString());
        E remove = (E) list.remove(0);
        System.out.println("take : " + remove);
        notifyAll();
        return remove;
    }


    public static void main(String[] args) {
        final MyBlocingQueue<Integer> myBlocingQueue = new MyBlocingQueue(10);
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            exec.execute(new TestRunnable(myBlocingQueue));
        }
        exec.shutdown();
    }

    static class TestRunnable implements Runnable{
        private final MyBlocingQueue<Integer> myBlocingQueue;

        TestRunnable(MyBlocingQueue<Integer> myBlocingQueue) {
            this.myBlocingQueue = myBlocingQueue;
        }

        @Override
        public void run() {
            Random random = new Random();
            int r = random.nextInt(1000);//该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n。
            //生成随机数,按照一定比率读取或者放入，可以更改！！！
            if (r < 300){
                myBlocingQueue.put(r);
            } else {
                myBlocingQueue.take();
            }
        }
    }



}
