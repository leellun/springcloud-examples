package com.newland.payment.service.impl;

import com.newland.payment.domain.Account;
import com.newland.payment.domain.Payment;
import com.newland.payment.domain.Response;
import com.newland.payment.mapper.AccountMapper;
import com.newland.payment.mapper.PaymentMapper;
import com.newland.payment.service.IPaymentService;
import com.newland.payment.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Slf4j
@Service("paymentService")
public class PaymentServiceImpl implements IPaymentService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    @Transactional//这里暂时伪代码替代
    public Response payment(String userAccount, BigDecimal cost, String requestKey) {
        boolean lock = false;
        try {
            //通过分布式锁，防止同一时刻多个支付操作
            if (lock = redisUtil.tryLock(requestKey)) {
                Account account = accountMapper.selectUser(userAccount);
                if (account == null) {
                    log.warn("异常操作，支付账户不存在");
                    return Response.fail(4, "扣款账户不存在");
                }
                Payment dbPayment = paymentMapper.selectPayment(requestKey);
                if (dbPayment != null) {
                    log.warn("异常操作，已经支付订单在此支付");
                    return Response.ok(100, "当前正在支付中");
                }
                Payment payment = new Payment(userAccount, cost, requestKey);
                paymentMapper.insert(payment);
                account.setBalance(account.getBalance().subtract(cost));
                accountMapper.updateAccount(account);
                return Response.ok(1, "支付成功");
            } else {
                log.warn("异常操作，连续请求");
                return Response.ok(1, "当前正在支付中");
            }
        } catch (Exception e) {
            log.error("异常操作",e);
            throw new RuntimeException("扣款失败", e);
        } finally {
            if (lock) {
                redisUtil.del(requestKey);
            }
        }
    }
}
