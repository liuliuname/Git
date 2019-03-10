package com.example.springboot.spring.controller;

import com.example.springboot.spring.service.IHelloWorldService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {

    @Test
    public void testHelloworld() {
        ApplicationContext ctx =  new ClassPathXmlApplicationContext("aop.xml");
        IHelloWorldService helloworldService =
                ctx.getBean("helloWorldService", IHelloWorldService.class);
        helloworldService.sayHello();
    }
}
