package com.example.springboot.spring.impl;

import com.example.springboot.spring.service.IHelloWorldService;


public class HelloWorldService implements IHelloWorldService {
    @Override
    public void sayHello() {
        System.out.println("============Hello World!");
    }
}
