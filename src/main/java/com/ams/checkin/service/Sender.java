package com.ams.checkin.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Sender
{

	private RabbitMessagingTemplate template;

	@Bean
	Queue queue()
	{
		return new Queue("CheckInQ", false);
	}

	public void send(Object message)
	{
		template.convertAndSend("CheckInQ", message);
	}
}