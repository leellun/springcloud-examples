package com.newland.springcloudsentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.newland.springcloudsentinel.entities.Response;

public class SentinelBlockHandler {
    public static Response handlerException(BlockException exception) {
        return new Response(4444, "按客戶自定义,global handlerException----1");
    }

    public static Response handlerException2(BlockException exception) {
        return new Response(4444, "按客戶自定义,global handlerException----2");
    }
}
