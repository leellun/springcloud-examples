package com.newland.springcloud.alibaba.controller;

import com.newland.springcloud.alibaba.domain.CommonResult;
import com.newland.springcloud.alibaba.domain.Order;
import com.newland.springcloud.alibaba.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther zzyy
 * @create 2020-02-26 15:24
 * http://192.168.10.100:2001/order/create?userId=1&productId=1&count=1&money=10
 */
@RestController
public class OrderController
{
    @Resource
    private OrderService orderService;


    @GetMapping("/order/create")
    public CommonResult create(Order order)
    {
        orderService.create(order);
        return new CommonResult(200,"订单创建成功");
    }
}
