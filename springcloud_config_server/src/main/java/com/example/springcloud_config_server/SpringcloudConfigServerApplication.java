package com.example.springcloud_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 *
 * @EnableDiscoveryClient和@EnableEurekaClient共同点就是：
 * 都是能够让注册中心能够发现，扫描到该服务。  拥有了服务注册的功能
 *
 * 不同点：@EnableEurekaClient只适用于Eureka作为注册中心，@EnableDiscoveryClient 
 * 可以是其他注册中心。
 * @EnableEurekaServer 该注解是激活eureka注册中心内容
 * @EnableConfigServer 该注解为配置文件为服务
 *
 * 服务都关闭时 client端还是能访问到内容 说明需要配置一些文件 具体待查
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudConfigServerApplication.class, args);
    }
}
