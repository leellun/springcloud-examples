package com.spring.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.spring.consumer.service.MailService;
import com.spring.producer.entity.MailContent;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender sender;
	@Autowired
	private SimpleMailMessage mailMessage;
	@Autowired
	private ThreadPoolTaskExecutor pool;

	public void sendMail(final MailContent mailContent) {
		this.pool.execute(new Runnable() {

			public void run() {
				mailMessage.setTo(mailContent.getTo());
				mailMessage.setSubject(mailContent.getSubject());
				mailMessage.setText(mailContent.getContent());
				sender.send(mailMessage);
			}
		});
	}

}
