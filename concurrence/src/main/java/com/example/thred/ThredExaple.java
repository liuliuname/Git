package com.example.thred;

public class ThredExaple extends Thread {

    private int ticket = 10;

    public void run() {
        for (int i = 0; i < 10; i++) {
            if (this.ticket > 0) {
                System.out.println(this.getName() + " 卖票：ticket" + this.ticket--);
            }
        }
    }

    public static void main(String[] args) {
        ThredExaple exaple = new ThredExaple();
        ThredExaple exaple1 = new ThredExaple();
        ThredExaple exaple2 = new ThredExaple();
        exaple.start();
        exaple1.start();
        exaple2.start();
    }
}
