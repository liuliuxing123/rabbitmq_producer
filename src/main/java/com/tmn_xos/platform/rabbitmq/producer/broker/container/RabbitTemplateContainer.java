package com.tmn_xos.platform.rabbitmq.producer.broker.container;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.tmn_xos.platform.rabbitmq.api.common.MessageType;
import com.tmn_xos.platform.rabbitmq.api.exception.MessageRunTimeException;
import com.tmn_xos.platform.rabbitmq.api.pojo.Message;
import com.tmn_xos.platform.rabbitmq.common.convert.GenericMessageConverter;
import com.tmn_xos.platform.rabbitmq.common.convert.RabbitMessageConverter;
import com.tmn_xos.platform.rabbitmq.common.serializer.Serializer;
import com.tmn_xos.platform.rabbitmq.common.serializer.SerializerFactory;
import com.tmn_xos.platform.rabbitmq.common.serializer.impl.JacksonSerializerFactory;
import com.tmn_xos.platform.rabbitmq.producer.broker.confirm.ConfirmCallback;
import com.tmn_xos.platform.rabbitmq.producer.broker.confirm.ConfirmQueueReturn;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: liuxing
 * @Description:  
 *  * 	topic毎に RabbitTemplateを生成
 *  *	1.	効率向上
 * @Date: 2020/6/27
 */
@Component
public class RabbitTemplateContainer {
	
	private static final Logger log = LoggerFactory.getLogger(RabbitTemplateContainer.class);
    /**
     * キャッシュ map<topic,template>
     */
	private Map<String, RabbitTemplate> rabbitMap = Maps.newConcurrentMap();

	private SerializerFactory serializerFactory = JacksonSerializerFactory.getInstance();

	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private ConfirmCallback confirmCallback;
	@Autowired
	ConfirmQueueReturn confirmQueueReturn;
	
    /**
     * ルーティングキーでtemplateを取得
     * @param message
     * @return
     * @throws MessageRunTimeException
     */
	public RabbitTemplate getTemplate(Message message) throws MessageRunTimeException {
		Preconditions.checkNotNull(message);
		String topic = message.getTopic();
		RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
		// 1.templateある場合直接に利用
		if(rabbitTemplate != null) {
			return rabbitTemplate;
		}
		log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);

		// 2.templateない場合、生成
		RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
		newTemplate.setExchange(topic);
		newTemplate.setRoutingKey(message.getRoutingKey());
		newTemplate.setRetryTemplate(new RetryTemplate());
		
		// 3.シリアルconverter設定
		Serializer serializer = serializerFactory.create();
		GenericMessageConverter gmc = new GenericMessageConverter(serializer);
		
		RabbitMessageConverter rmc = new RabbitMessageConverter(gmc);
		newTemplate.setMessageConverter(rmc);

		// 4.HAの場合、コールバック
		String messageType = message.getMessageType();
		if(!MessageType.RAPID.equals(messageType)) {
		    // broker成功確認
			newTemplate.setConfirmCallback(confirmCallback);
			//　キュー成功確認
			newTemplate.setReturnCallback(confirmQueueReturn);
		}
		
		rabbitMap.putIfAbsent(topic, newTemplate);
		
		return rabbitMap.get(topic);
	}

}
