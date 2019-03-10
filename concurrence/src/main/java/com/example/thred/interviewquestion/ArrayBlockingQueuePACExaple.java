package com.example.thred.interviewquestion;


import com.example.thred.producerAndConsumer.Phone;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 使用ArrayBlockingQueue 实现生产者与消费者模式
 */
public class ArrayBlockingQueuePACExaple {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        Thread thread = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread1 = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread3 = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread4 = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread5 = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread6 = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread7 = new Thread(new Producter((ArrayBlockingQueue) queue));
        Thread thread8 = new Thread(new Producter((ArrayBlockingQueue) queue));

        Thread thread2 = new Thread(new Consu((ArrayBlockingQueue)    queue));
        thread1.start();
        thread2.start();
        thread.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();



    }

}

/**
 * 消费者
 */
class Producter implements Runnable {

    private ArrayBlockingQueue arrayBlockingQueue;

    // private Phone phone;
    public Producter(ArrayBlockingQueue arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
                arrayBlockingQueue.put(new Phone());
                System.out.println("Producter 生产");
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

    }
}
class Consu implements Runnable{

    private ArrayBlockingQueue arrayBlockingQueue;
    public Consu(ArrayBlockingQueue arrayBlockingQueue){
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(1000);
                Phone take = (Phone) arrayBlockingQueue.take();
                System.out.println("Consu 消费编号 " + take.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}