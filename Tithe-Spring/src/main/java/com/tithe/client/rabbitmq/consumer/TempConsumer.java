package com.tithe.client.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.tithe.entity.RelationEntity;

@Service
public class TempConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TempConsumer.class);
	
	@RabbitListener(queues = {"tempQueue"})
	public void consume(RelationEntity relation) {
		LOGGER.info(String.format("Message Received: %s", relation));
		
	}

}
