package com.example.thred.lock;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExaple {

    Integer count = 10;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public static void main(String[] args) {

        final CountDownLatchExaple countDownLatchExaple = new CountDownLatchExaple();
        //模拟 10个人 同时扣减库存

        final CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {

            new Thread(new Runnable() {
                    @Override
                    public void run() {
                          try {
                                countDownLatch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        synchronized (countDownLatchExaple){
                        System.out.println(Thread.currentThread().getName() + "执行 库存为" + countDownLatchExaple.getCount());
                        countDownLatchExaple.setCount(countDownLatchExaple.getCount()-1);
                        System.out.println(Thread.currentThread().getName() +"执行 库存后" + countDownLatchExaple.getCount());
                    }
                }
            }).start();
        }
        countDownLatch.countDown();
        System.out.println(Thread.currentThread().getName() +  "执行开始");


        System.out.println(Thread.currentThread().getName() +  "执行结束");
    }

}
