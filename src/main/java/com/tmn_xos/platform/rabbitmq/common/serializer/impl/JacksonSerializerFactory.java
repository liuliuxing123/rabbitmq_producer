package com.tmn_xos.platform.rabbitmq.common.serializer.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmn_xos.platform.rabbitmq.api.pojo.Message;
import com.tmn_xos.platform.rabbitmq.common.serializer.Serializer;
import com.tmn_xos.platform.rabbitmq.common.serializer.SerializerFactory;
import com.tmn_xos.platform.rabbitmq.producer.broker.confirm.ConfirmCallback;

/**
 * 
 * @Author: liuxing
 * @Description:
 * @Date: 2020/6/27
 */

public final class JacksonSerializerFactory implements SerializerFactory {
	private static volatile SerializerFactory instance;

	private JacksonSerializerFactory() {

	}
	public static SerializerFactory getInstance() {
		SerializerFactory result = instance;
		if (result != null) {
			return result;
		}
		synchronized (JacksonSerializerFactory.class) {
			if (instance == null) {
				instance = new JacksonSerializerFactory();
			}
			return instance;
		}
	}

	@Override
	public Serializer create() {
		return JacksonSerializer.createParametricType(Message.class);
	}

}
