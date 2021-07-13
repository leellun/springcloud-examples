package com.newland.activemq.filtration;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
/**
 * 消息过滤发送
 * @author 28974
 *
 */
public class FiltrationProducer {
	public void sendMessage() {
		ConnectionFactory factory = null;
		javax.jms.Connection connection = null;
		Destination destination = null;
		Session session = null;
		MessageProducer producer = null;
		Message message = null;
		try {
			factory=new ActiveMQConnectionFactory("admin","admin","tcp://192.168.3.44:61616");
			connection=factory.createConnection();
			session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination=session.createQueue("filtration");
			producer=session.createProducer(destination);
			connection.start();
			for(int i=0;i<100;i++) {
				message=session.createObjectMessage(i);
				message.setBooleanProperty("validate", true);
				message.setIntProperty("index", i);
				producer.send(message,DeliveryMode.PERSISTENT,i%9+1,2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (producer != null) {
				try {
					producer.close();
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
		FiltrationProducer producer=new FiltrationProducer();
		producer.sendMessage();
	}
}
