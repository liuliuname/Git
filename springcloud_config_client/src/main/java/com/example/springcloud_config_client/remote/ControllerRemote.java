package com.example.springcloud_config_client.remote;

import com.example.springcloud_config_client.hystrix.HelloRemoteHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * name:远程服务名，及spring.application.name配置的名称
 * 此类中的方法和远程服务中contoller中的方法名和参数需保持一致。
 *
 * feign不支持下划线"_"
 * @FeignClient 发现其他服务
 * fallback 失败走熔断器的方法
 */
@FeignClient(name = "springcloudserver",fallback = HelloRemoteHystrix.class)
public interface ControllerRemote {

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);
}
