package com.example.springcloud_config_client.controller;

import com.example.springcloud_config_client.remote.ControllerRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中
public class ConfigController {
    /**
     * LoadBalanced 使用ribbon配置负载均衡
     * @return
     */
    @Autowired
    RestTemplate restTemplate;

    @Value("${neo.hello}")
    private String hello;

    @Autowired
    public ControllerRemote controllerRemote;

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name){
        System.out.println("接口被调用获取git配置文件内容为="+hello);
        return this.controllerRemote.hello(name) +"----------github ="+name;
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @RequestMapping("/")
    public String index(){
        Object
        ServiceInstance choose = loadBalancerClient.choose("springcloudserver");
        String host = choose.getHost();
        int port = choose.getPort();
        System.out.println(host + port);
        return restTemplate.getForObject("http://springcloudserver/hello?name=111",String.class);
    }

}
