package com.spring.producer.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.spring.producer.entity.MailContent;
import com.spring.producer.service.MessageService;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	@Autowired
	private JmsTemplate template;

	public void sendMessage(String from, String to, String subject, String content) {
		if (to.indexOf(";") != -1) {
			String[] tos = to.split(";");
			for (String t : tos) {
				if (StringUtils.isEmpty(t)) {
					continue;
				} else {
					MailContent mail = this.transfer2Mail(from, t, subject, content);
					this.sendMail(mail);
				}
			}
		} else {
			MailContent mail = this.transfer2Mail(from, to, subject, content);
			this.sendMail(mail);
		}
	}

	private MailContent transfer2Mail(String from, String to, String subject, String content) {
		MailContent mail = new MailContent();
		mail.setFrom(from);
		mail.setContent(content);
		mail.setTo(to);
		mail.setSubject(subject);
		return mail;
	}

	private void sendMail(final MailContent mail) {
		template.send(new MessageCreator() {

			public Message createMessage(Session session) throws JMSException {
				Message message = session.createObjectMessage(mail);
				return message;
			}
		});
	}

}
