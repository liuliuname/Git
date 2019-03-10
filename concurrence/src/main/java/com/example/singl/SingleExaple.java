package com.example.singl;

public class SingleExaple {

    private SingleExaple(){}

    public static SingleExaple singleExaple = new SingleExaple();

    public static SingleExaple getInstance(){
        return singleExaple;
    }
}
