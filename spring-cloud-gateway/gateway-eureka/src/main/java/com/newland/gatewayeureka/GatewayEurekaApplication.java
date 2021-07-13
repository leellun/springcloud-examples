package com.newland.gatewayeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GatewayEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayEurekaApplication.class, args);
    }

}
