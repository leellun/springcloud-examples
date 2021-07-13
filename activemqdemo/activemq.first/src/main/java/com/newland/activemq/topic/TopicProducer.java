package com.newland.activemq.topic;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.newland.activemq.first.TextProducer;
/**
 * topic发送
 * @author 28974
 *
 */
public class TopicProducer {
	/**
	 * 發送消息到ActiveMQ中，具體的消息内容为参数消息 开发JMS相关代码过程中，使用的接口类型都是javax.jms包下的类型。
	 * 
	 * @param datas 消息内容
	 */
	public void sendTextMessage(String datas) {
		// 连接工厂
		ConnectionFactory factory = null;
		// 连接
		javax.jms.Connection connection = null;
		// 目的地
		Destination destination = null;
		// 会话
		Session session = null;
		// 消息发送者
		MessageProducer producer = null;
		// 消息对象
		Message message = null;
		try {
			factory = new ActiveMQConnectionFactory("admin", "admin", "tcp://192.168.3.44:61616");

			// 通过工厂，创建连接对象
			// 可以再创建连接共厂时，只传递连接地址，不传递用户信息。
			connection = factory.createConnection();
			// 建议启动连接，消息的发送者不是必须启动连接，消息的消费者必须启动连接
			// 如果有其他配置，配置完后再启动
			connection.start();

			/*
			 * 通过连接对象，创建会话对象，必须绑定目的地 创建会话的时候，必须传递两个参数，分别代表是否支持事务和如何确认消息处理 transacted -
			 * 是否支持事务 true - 支持事务，第二个参数默认无效，建议传递的数据时Session.SESSION_TRANSACTED false -
			 * 不支持事务，第二个参数必须传递，且必须有效。
			 * 
			 * acknowledgeMode - 消息处理模式 AUTO_ACKNOWLEDGE - 自动确认消息，消息的消费者处理消息后，自动确认。
			 * CLIENT_ACKNOWLEGE - 客户端手动确认，消息的消费者处理后，必须手工确认。 DUPS_OK_ACKNOWLEGE -
			 * 有副本的客户端手动确认。一个消息可以多次处理，可以降低session的消耗，在可以容忍重复消息时使用。（不推荐）
			 */
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			destination = session.createTopic("first-topic");

			/**
			 * 通过会话对象，创建消息的发送者producer 创建的消息发送者，发送消息一定到指定的目的地中
			 */
			producer = session.createProducer(destination);

			// 创建文本消息对象，作为具體数据内容的载体
			message = session.createTextMessage(datas);

			// 发送消息到ActiveMQ中的目的地
			producer.send(message);
			System.out.println("消息发送成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) {
		TopicProducer producer = new TopicProducer();
		producer.sendTextMessage("测试ActivieMQ topic");
	}
}
