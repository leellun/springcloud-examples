package com.newland.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class CloudConsulProviderPayment8006Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudConsulProviderPayment8006Application.class, args);
    }

    @RequestMapping("/helloConsul")
    public String helloZk() {
        //instance name必须与上面一样，不能像Eureka那样可以大写(cloud-zk-provider)
        return "hello consul";
    }
}
