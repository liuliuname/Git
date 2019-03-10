package com.example.thred.producerAndConsumer;

public class Producer implements Runnable {

   // private Storage<Phone> storage;

    private Product Product;

    public Producer(Product Product) {
        this.Product = Product;
    }
    public void run() {
        while (true){
            Product.producer();
            try {
                Thread.sleep(10);//每隔10毫秒生产一个产品
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

