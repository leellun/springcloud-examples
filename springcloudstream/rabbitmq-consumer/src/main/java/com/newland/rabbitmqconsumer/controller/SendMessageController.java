package com.newland.rabbitmqconsumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyy
 * @create 2020-02-22 11:17
 */
@RestController
@EnableBinding(Sink.class)
public class SendMessageController {
    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void receiveMessage(Message<String> message) {
        System.out.println("接受消息-》" + message.getPayload() + "---" + serverPort);
    }

}
