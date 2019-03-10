package com.example.thred.producerAndConsumer;

public class ProducerAndConsumer {


    /**
     * 生产者 消费者
     *
     * 公用商品类  里面包括生产方法 消费方法
     *
     *
     *
     * @param args
     */
    public static void main(String[] args) {
       // Storage<Phone> storage = new Storage<Phone>();
        Product Product = new Product();
        new Thread(new Producer(Product)).start();
        new Thread(new Producer(Product)).start();

        new Thread(new Consumer(Product)).start();
    }


}
