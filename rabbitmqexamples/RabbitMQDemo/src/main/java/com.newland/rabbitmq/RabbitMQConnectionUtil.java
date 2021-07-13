package com.newland.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnectionUtil {
    public static Connection getConnection() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("leellun");
        factory.setPassword("liulun666");
        factory.setVirtualHost("/");
        factory.setHost("192.168.10.103");
        factory.setPort(5672);
        try {
            Connection connection = factory.newConnection("app:audit component:event-consumer");
            return connection;
        } catch (Exception e) {
            return null;
        }
    }
}
