package com.example.springcloud.provider.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * springcloud 服务提供者
 *
 */
@RestController
public class ProviderController {

    @RequestMapping("/providerTest")
    public String providerTest(){
        System.out.println("服务者被调用");
        return "hello springcloud2";
    }



}
