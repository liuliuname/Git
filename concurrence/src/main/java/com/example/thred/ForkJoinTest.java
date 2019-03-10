package com.example.thred;

public class ForkJoinTest {

    public static void main(String[] args) {

    }

}

class sumTest implements  Runnable{

    public Double sum;

    @Override
    public void run() {
        for(int i = 0; i < 100000;i++){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sum = sum+i;
        }
    }
}
