package com.example.springcloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * springcloud 消费者
 */
@RestController
public class ConsumerController {

    /**
     * LoadBalanced 使用ribbon配置负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @RequestMapping("/consumerTest")
    public String consumerTest(){
        /**
         * 根据接口的服务名 获取具体的实例
         */
        ServiceInstance choose = loadBalancerClient.choose("provider");
        String host = choose.getHost();
        int port = choose.getPort();
        //return restTemplate.getForObject("http://"+host+":"+port+"/providerTest",String.class);
        return restTemplate.getForObject("http://provider/providerTest",String.class);
    }


    /**
     * 使用loadBalancerClient 需要硬编码拼接获取实例  不选择使用  可以使用LoadBalancedz注解根据服务名配置负载均衡
     */
    @RequestMapping("/consumer/getInterfaceInfo")
    public void getInterfaceInfo(){
        ServiceInstance choose = loadBalancerClient.choose("provider");
        System.out.println(choose.getPort());
    }

}
