package com.newland.gatewaydemo;

import com.newland.gatewaydemo.custom.RequestTimeFilter;
import com.newland.gatewaydemo.custom.RequestTimeGatewayFilterFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class GatewaydemoApplication {
    @Bean
    public RequestTimeGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new RequestTimeGatewayFilterFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewaydemoApplication.class, args);
    }

    // tag::route-locator[]
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        String httpUri2 = "https://www.baidu.com";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("mycmd7")
                                        .setFallbackUri("forward:/fallback")))
                        .uri(httpUri))
                .route(p -> p.path("/p/86937325")
                        .filters(f -> f
                                .filter(new RequestTimeFilter())
                                .addResponseHeader("X-Response-Defautl-Foo", "Default-Bar"))
                        .uri("https://zhuanlan.zhihu.com"))
                .build();
    }
    // end::route-locator[]

    // tag::fallback[]
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }
    // end::fallback[]
}
