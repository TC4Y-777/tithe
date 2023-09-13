package com.tithe.client.rabbitmq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tithe.entity.RelationEntity;

@Service
public class TempProducer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TempProducer.class);
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessage(String message) {
		RelationEntity relation = new RelationEntity(23L, "Niece");
		LOGGER.info(String.format("Message sent: %s", relation));
		
		rabbitTemplate.convertAndSend("tempTopicExchange", "tempQueueRoutingKey", relation);
	}

}
