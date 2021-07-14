package com.newland.payment.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.newland.payment.domain.Response;
import com.newland.payment.service.IPaymentService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/bank")
public class PaymentController {
    @Autowired
    @Qualifier("paymentService")
    private IPaymentService paymentService;
    RateLimiter limiter = RateLimiter.create(300);

    @PostMapping("/account/cost.json")
    public Response payment(String userAccount, BigDecimal cost, String requestKey) {
        try {
            //接口300QPS  直接用AQS实现都行
            if (!limiter.tryAcquire(1)) return Response.fail(40, "拒绝请求");
            if (StringUtils.isEmpty(userAccount)) {
                return Response.fail(2, "用户账号不能为空");
            }
            if (cost.compareTo(BigDecimal.ZERO) <= 0) {
                return Response.fail(3, "金额必须为正");
            }
            if (StringUtils.isEmpty(requestKey)) {
                return Response.fail(4, "requestKey不许不为空");
            }
            return paymentService.payment(userAccount, cost, requestKey);
        } catch (Exception e) {
            log.error("订单支付出现异常", e);
            return Response.fail(202, "接口访问异常");
        }
    }
}
