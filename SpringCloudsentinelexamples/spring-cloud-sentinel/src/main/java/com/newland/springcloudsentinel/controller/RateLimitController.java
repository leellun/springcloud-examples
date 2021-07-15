package com.newland.springcloudsentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.newland.springcloudsentinel.entities.Payment;
import com.newland.springcloudsentinel.entities.Response;
import com.newland.springcloudsentinel.handler.SentinelBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyy
 * @create 2020-02-25 15:04
 */
@RestController
public class RateLimitController {
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public Response byResource() {
        return new Response(200, "按资源名称限流测试OK", new Payment(2020L, "serial001"));
    }

    public Response handleException(BlockException exception) {
        return new Response(444, exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public Response byUrl() {
        return new Response(200, "按url限流测试OK", new Payment(2020L, "serial002"));
    }


    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = SentinelBlockHandler.class,
            blockHandler = "handlerException2")
    public Response customerBlockHandler() {
        return new Response(200, "按客戶自定义", new Payment(2020L, "serial003"));
    }
}