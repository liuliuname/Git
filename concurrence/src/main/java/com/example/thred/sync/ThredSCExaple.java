package com.example.thred.sync;


/**
 * 多线程 生产者与消费者问题
 */
public class ThredSCExaple {

    //仓库
    static class Entrepot{
        private Integer count = 10;
        static Entrepot entrepot = new Entrepot();

        private Entrepot() {}

        public static Entrepot getInstance(){
            return entrepot;
        }
        public synchronized void push(Entrepot entrepot) throws InterruptedException {
            // 库存 大于 10个 停止生产
           // while (true){
                if (count >= 10){
                    entrepot.wait();//挂起
                }
                while(true){
                    count = count + 1;
                    System.out.println(Thread.currentThread().getName() + "生产者 count"+ count);
                    if(count >= 10){
                        break;
                    }
                }

                //notifyAll();
           // }
        }

        public synchronized void pop(Entrepot entrepot) throws InterruptedException {
            //while(true){
                if(count < 1){
                    System.out.println(Thread.currentThread().getName() + "消费者库存不足 count =" +count);
                    //entrepot.wait();
                }
                while(true){
                    Thread.sleep(1000);
                    count = count -1;
                    System.out.println(Thread.currentThread().getName() + "消费者 count"+count);
                    if(count < 1){
                        notifyAll();
                        break;
                    }
                }

               /* if(count < 10){
                    entrepot.notify();
                }*/
                //entrepot.wait();
            //}
        }
    }

    //生产者
    static class Product implements Runnable{

        public void run() {
            Entrepot entrepot = Entrepot.getInstance();
            try {
                entrepot.push(entrepot);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //生产者
    static class Conumer implements Runnable{

        public void run() {
            Entrepot entrepot = Entrepot.getInstance();
            try {
                entrepot.pop(entrepot);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Product p1 = new Product();
        Conumer c1 = new Conumer();

        Thread thread = new Thread(p1);
        Thread thread1 = new Thread(c1);
        thread.start();
        thread1.start();

    }

}
