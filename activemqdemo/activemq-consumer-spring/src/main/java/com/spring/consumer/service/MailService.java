package com.spring.consumer.service;

import com.spring.producer.entity.MailContent;

public interface MailService {

	void sendMail(MailContent mailContent);

}
