package com.example.thred.sync;

public class Something{
        public synchronized void isSyncA() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : isSyncA");
                }
            } catch (InterruptedException ie) {
            }
        }
        public synchronized void isSyncB() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : isSyncB");
                }
            } catch (InterruptedException ie) {
            }
        }
        public static synchronized void cSyncA() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : cSyncA");
                }
            } catch (InterruptedException ie) {
            }
        }
        public static synchronized void cSyncB() {
            try {
                for (int i = 0; i < 5; i++) {
                    Thread.sleep(100); // 休眠100ms
                    System.out.println(Thread.currentThread().getName() + " : cSyncB");
                }
            } catch (InterruptedException ie) {
            }
        }

    public static void main(String[] args) {

        //x.isSyncA()与x.isSyncB()  不能同时 访问 共享实例锁
       /*   final Something something = new Something();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                something.isSyncA();
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                something.isSyncB();
            }
        });

        thread.start();
        thread1.start();*/

        //x.isSyncA()与y.isSyncA()  可以同时访问 因为非同一对象

       /* final Something x = new Something();
        final Something y = new Something();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                x.isSyncA();
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                y.isSyncA();
            }
        });

        thread.start();
        thread1.start();*/
        //x.cSyncA()与y.cSyncB()
        //。因为cSyncA()和cSyncB()都是static类型，x.cSyncA()相当于Something.isSyncA()，y.cSyncB()相当于Something.isSyncB()，因此它们共用一个同步锁，不能被同时反问。
        final Something x = new Something();
        final Something y = new Something();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                x.cSyncA();
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                y.cSyncB();
            }
        });

        thread.start();
        thread1.start();
        //x.isSyncA()与Something.cSyncA()
        // 可以同时访问因为isSyncA()是实例方法，x.isSyncA()使用的是对象x的锁；而cSyncA()是静态方法，Something.cSyncA()可以理解对使用的是“类的锁”。因此，它们是可以被同时访问的。
        //实例锁 和 全局锁

        /*final Something x = new Something();
       // final Something y = new Something();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                x.isSyncA();
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                Something.cSyncA();
            }
        });

        thread.start();
        thread1.start();*/
    }
}
