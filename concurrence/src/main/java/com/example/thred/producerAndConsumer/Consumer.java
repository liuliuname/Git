package com.example.thred.producerAndConsumer;

public class Consumer implements Runnable {

    private Storage<Phone> storage;

    private Product Product;

    public Consumer(Product Product) {
        this.Product = Product;
    }

    public void run() {
        while (true){
            Product.consumer();
            try {
                Thread.sleep(100);//每隔100毫秒消费一个
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

