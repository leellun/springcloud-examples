package com.spring.producer.service;

public interface MessageService {
	public void sendMessage(String from, String to, String subject, String content);
}
