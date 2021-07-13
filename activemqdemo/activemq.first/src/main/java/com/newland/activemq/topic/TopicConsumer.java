package com.newland.activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * topic接收
 * @author 28974
 *
 */
public class TopicConsumer {

	public String receiveTextMessage() {
		String resultCode = "";
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		// 消息的消费者，用于接收消息的对象
		MessageConsumer consumer = null;
		Message message = null;
		try {
			factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.3.44:61616");
			connection = factory.createConnection();
			// 消息的消费者必须启动连接，否则无法处理消息
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("first-topic");
			consumer = session.createConsumer(destination);
			// 接收队列中的消息
			message = consumer.receive();

			resultCode = ((TextMessage) message).getText();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (consumer != null) {
				try {
					consumer.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		return resultCode;
	}

	public static void main(String[] args) {
		TopicConsumer consumer = new TopicConsumer();
		String text = consumer.receiveTextMessage();
		System.out.println("消息内容是：" + text);
	}
}
