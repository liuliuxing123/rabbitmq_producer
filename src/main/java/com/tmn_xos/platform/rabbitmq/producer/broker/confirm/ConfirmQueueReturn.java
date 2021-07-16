package com.tmn_xos.platform.rabbitmq.producer.broker.confirm;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

@Component
public class ConfirmQueueReturn implements RabbitTemplate.ReturnCallback {

	@Override
	public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
		message.getMessageProperties();
		System.err.println("return exchange:" + exchange);
		System.err.println("return routingKey:" + routingKey);
		System.err.println("return replyCode:" + replyCode);
		System.err.println("return replyText:" + replyText);
	}

}
