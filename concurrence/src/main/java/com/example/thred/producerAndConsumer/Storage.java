package com.example.thred.producerAndConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * 仓库类，用于管理产品的生产、消费和存储。
 */
public class Storage<T> {
    private int index = 0;
    private static final int MAX = 10;//最大容量
    private List<T> storages = new ArrayList<T>(MAX);//存储集合

    public synchronized void produce(T item) {
        while (index >= MAX) {// 判断仓库满了，则等待。
            try {
                System.out.println("仓库满了，等待中...");
                this.wait();
                System.out.println("仓库不满了，开始生产");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生产>>" + item.toString());
        storages.add(item);
        index++;   //先添加item，在进行加1操作
        notifyAll();  //唤醒在此对象监视器上等待的单个线程，即消费者线程
    }

    public synchronized T consume() {
        while (index <= 0) {// 判断仓库空了，则等待。
            try {
                System.out.println("仓库为空，等待中...");
                this.wait();
                System.out.println("仓库不为空，开始消费");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        index--;//先进行减1操作，再remove
        T item = storages.remove(index);
        System.out.println("消费>>" + item.toString());
        notifyAll();
        return item;
    }
}

