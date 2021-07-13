package com.spring.consumer.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spring.consumer.service.MailService;
import com.spring.producer.entity.MailContent;

@Component("emailMessage")
public class EmailMessageListener implements MessageListener{
	@Autowired
	private MailService service;
	public void onMessage(Message message) {
		System.out.println("==="+message);
		try {
			if(message instanceof ObjectMessage) {
				ObjectMessage oMessage=(ObjectMessage) message;
				if(oMessage.getObject() instanceof MailContent) {
					MailContent mailContent=(MailContent) oMessage.getObject();
					this.service.sendMail(mailContent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
