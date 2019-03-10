package com.example.singl;

/**
 * 饿汉式 线程安全
 */
public class BadmashSingleExaple {

    private BadmashSingleExaple(){}

    private static BadmashSingleExaple instance = new BadmashSingleExaple();

    public static BadmashSingleExaple getInstance(){
        return instance;
    }
}
