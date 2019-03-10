package com.example.thred.producerAndConsumer;

public class Phone {

    private int id;// 手机编号

    public Phone() {
        id = id++;
    }

    @Override
    public String toString() {
        return "手机编号：" + id;
    }

}
