package com.example.thred.interviewquestion;


/**
 * 面试题3:自己编写代码,实现生产者-消费者模型功能.内容自由发挥,只需要表达思想.
 *
 *
 * 如何让仓库资源共享 是个问题
 *
 */
public class InterviewQuestionExaple2 {

    public static void main(String[] args) {
        Storege storege = new Storege();
        while (true){
            Thread proThred = new Thread(new Product(storege));
            Thread conThred = new Thread(new Consomer(storege));
            proThred.start();
            conThred.start();
        }
    }

}

/**
 * 生产者
 */
class Product implements  Runnable{
    Storege storege;
    public Product(Storege storege){
        this.storege = storege;
    }
    @Override
    public void run() {
        storege.add();
    }
}

/**
 * 消费者
 */
class Consomer implements  Runnable{
    Storege storege;
    public Consomer(Storege storege){
        this.storege = storege;
    }
    @Override
    public void run() {
        storege.remove();
    }
}

/**
 * 仓库
 */
class Storege{
    Integer soregeCount = 10;
    public synchronized void add(){
        while (soregeCount >= 10){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        soregeCount = soregeCount + 1;
        System.out.println("生产者 soregeCount= " + soregeCount);
        notify();
    }

    public synchronized void remove(){
        while (soregeCount <= 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        soregeCount = soregeCount - 1;
        System.out.println("消费者 soregeCount= " + soregeCount);
        notify();
    }

}

