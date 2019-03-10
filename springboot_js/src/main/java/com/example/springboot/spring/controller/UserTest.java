package com.example.springboot.spring.controller;

import com.example.springboot.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    public static void main(String[] args) {
        /**
         * 使用ioc注入
         */
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService.getUser("嘿嘿"));
    }
}
