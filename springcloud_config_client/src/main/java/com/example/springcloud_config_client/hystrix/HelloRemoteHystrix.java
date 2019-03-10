package com.example.springcloud_config_client.hystrix;

import com.example.springcloud_config_client.remote.ControllerRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloRemoteHystrix implements ControllerRemote {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" +name+", this messge send failed ";
    }
}
