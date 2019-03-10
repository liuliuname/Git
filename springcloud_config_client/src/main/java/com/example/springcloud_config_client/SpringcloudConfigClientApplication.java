package com.example.springcloud_config_client;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @EnableFeignClients 注解含义 启用feign进行远程调用 Feign是一个声明式Web Service客户端
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication()
public class SpringcloudConfigClientApplication {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        return new RandomRule();
    }
    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConfigClientApplication.class, args);
    }
}
