package com.example.thred.interviewquestion;

import com.example.thred.producerAndConsumer.Phone;

import java.util.concurrent.ArrayBlockingQueue;

class Producer implements Runnable{
    private int i =0;
    private final ArrayBlockingQueue<Phone> queue;

    public Producer(ArrayBlockingQueue<Phone> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            produce();
        }
    }

    public void produce(){
        try {

            Phone bread = new Phone();
            queue.put(bread);
            System.out.println("Producer:"+bread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//消费者 Consumrer.java 的实现
class Consumer1 implements Runnable{

    private final ArrayBlockingQueue<Phone> queue;

    public Consumer1(ArrayBlockingQueue<Phone> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            consume();
        }
    }

    public void consume(){
        try {
            Phone bread = queue.take();
            System.out.println("consumer:"+bread);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


public class Test {

    public static void main(String[] args) {
        new Thread(new Producer(queue)).start();
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer1(queue)).start();
        new Thread(new Consumer1(queue)).start();
        new Thread(new Consumer1(queue)).start();
    }

    private static int capacity = 10;
    private static ArrayBlockingQueue<Phone> queue = new ArrayBlockingQueue<Phone>(capacity);
}