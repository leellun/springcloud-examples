package com.newland.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableDiscoveryClient
public class CloudConsulConsumerOrder8007Application {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(CloudConsulConsumerOrder8007Application.class, args);
    }
    @RequestMapping("/helloProviderConsul")
    public String helloZk() {
        //instance name必须与上面一样，不能像Eureka那样可以大写(cloud-zk-provider)
        return restTemplate.getForObject("http://cloud-consul-provider-payment/helloConsul", String.class);
    }
}
