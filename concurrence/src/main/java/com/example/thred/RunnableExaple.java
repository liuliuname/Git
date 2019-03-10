package com.example.thred;

/**
 * 使用多线程实现Runnable接口 好于使用继承Thread类 因为使用Runnable接口可以对资源进行共享
 */
public class RunnableExaple implements Runnable {

    public int ticket = 10;
    public void run() {
        for(int i = 0; i < 10; i++){
            if(this.ticket > 0){
                System.out.println(Thread.currentThread().getName() +" 卖票：ticket"+this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        RunnableExaple exaple = new RunnableExaple();
        Thread thread1 = new Thread(exaple);
        Thread thread2 = new Thread(exaple);
        Thread thread3 = new Thread(exaple);
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
