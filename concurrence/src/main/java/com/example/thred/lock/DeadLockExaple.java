package com.example.thred.lock;

/**
 *
 * 死锁案例  产生死锁原因  两个线程 A B  A去访问B的方法 B去访问A的方法
 *
 * 对象a使用独立线程去调用对象b的方法。
 *
 * 对象b使用独立线程去调用对象a的方法。
 *
 * 对象a在调用b的方法之前对自己加锁，调用对象b后对对象b加锁。
 *
 * 对象b在调用a的方法之前对自己加锁，调用对象a后对对象a加锁。
 * 2、死锁形成原因解读
 *
 * a首先锁了自己，b锁了自己，
 *
 * a去拿b的锁，发现b已经锁定了，则等待b的锁释放
 *
 * b去拿a的锁，发现a已经锁定，则等待a释放。
 *
 * 就这样a等b,b也等a，造成了死锁的问题。
 *
 * 3、死锁的必要条件
 *
 * 两个或两个以上的线程在活动
 *
 * 某个线程拿到了一个锁以后，还想去拿第二个锁，即锁的嵌套
 *
 *
 *
 */
public class DeadLockExaple {

    public static void main(String[] args) {
        A a = new A();
        //a.writeA();
        B b = new B(a);
        a.setB(b);
       // b.writeB();
        new Thread(a).start();
        new Thread(b).start();
    }
}


class A implements Runnable{
    private static final Object A = new Object();
    private B b;
    public A(){
    }
    @Override
    public void run() {
        while (true){
            synchronized (b){
                b.writeB();
            }
        }
    }

    public void writeA(){
        synchronized (b){
            System.out.println("a方法");
        }
    }

    public void setB(B b){
        this.b = b;
    }

}

class B implements Runnable{

    private static final Object B = new Object();
    private A a;
    public B(A a){
        this.a = a;
    }

    @Override
    public void run() {
        while (true){
            synchronized (a){
                a.writeA();
            }
        }
    }

    public void writeB(){
        synchronized (a){
            System.out.println("b方法");
        }
    }

}
