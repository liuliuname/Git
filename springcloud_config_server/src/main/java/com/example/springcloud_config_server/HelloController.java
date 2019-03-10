package com.example.springcloud_config_server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        System.out.println("被执行");
        return "hello "+name+"，this is first messge2222";
    }
}
