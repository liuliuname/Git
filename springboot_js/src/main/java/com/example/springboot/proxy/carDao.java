package com.example.springboot.proxy;

public class carDao implements carDaoInte{

    @Override
    public void save() {
        System.out.println("save...");
    }
}
