package com.example.thred.producerAndConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * 仓库类，用于管理产品的生产、消费和存储。
 */
public class Product<T> {
    private int index = 0;
    private static final int MAX = 10;//最大容量
    private List<T> storages = new ArrayList<T>(MAX);//存储集合
    private Integer count = 0;


    public synchronized void producer(){
        if(count >10){
            System.out.println("库存满 等待");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("库存不足 开始生产");
        }
        count = count + 1;
        notifyAll();
    }


    public synchronized void consumer(){
        if(count < 1){
            System.out.println("库存不足 停止消费");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("库存充足 开始消费");
        }
        count = count -1 ;
        notifyAll();
    }


}

