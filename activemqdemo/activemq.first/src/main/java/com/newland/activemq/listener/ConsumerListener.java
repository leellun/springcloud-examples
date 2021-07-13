package com.newland.activemq.listener;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息监听
 * @author 28974
 *
 */
public class ConsumerListener {
	public void consumMessage() {
		ConnectionFactory factory = null;
		Connection connection = null;
		Session session = null;
		Destination destination = null;
		// 消息的消费者，用于接收消息的对象
		MessageConsumer consumer = null;
		try {
			factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.3.44:61616");
			connection = factory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			destination = session.createQueue("first-listener");
			consumer = session.createConsumer(destination);

			consumer.setMessageListener(new MessageListener() {
				/**
				 * 监听器一旦注册，永久生效（consumer线程不关闭）。 只要消息未处理，自动调用onMessage方法，处理消息。
				 * 监听器可以注册若干，注册多个监听器，类似集群。 ActiveMQ自动的循环调用多个注册器，处理队列中的消息，实现并行处理。
				 * @param message 未處理消息
				 */
				public void onMessage(Message message) {
					try {
						//確認方法，代表consumer已經處理消息
						message.acknowledge();
						ObjectMessage oMessage=(ObjectMessage) message;
						Object data=oMessage.getObject();
						System.out.println(data);
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});
			System.in.read();
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
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		ConsumerListener listener=new ConsumerListener();
		listener.consumMessage();
	}
}
