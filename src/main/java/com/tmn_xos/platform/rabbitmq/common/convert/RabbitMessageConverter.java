package com.tmn_xos.platform.rabbitmq.common.convert;

import com.google.common.base.Preconditions;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter delegate;

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        Preconditions.checkNotNull(genericMessageConverter);
        this.delegate = genericMessageConverter;
    }

    /**
     * com.itage.rabbit.api.pojo.Message -> org.springframework.amqp.core.Message
     * @param object
     * @param messageProperties 
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        com.tmn_xos.platform.rabbitmq.api.pojo.Message message = (com.tmn_xos.platform.rabbitmq.api.pojo.Message)object;
        messageProperties.setDelay(message.getDelayMills());
        return this.delegate.toMessage(object, messageProperties);
    }

    /**
     * org.springframework.amqp.core.Message -> com.itage.rabbit.api.pojo.Message
     * @param message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        com.tmn_xos.platform.rabbitmq.api.pojo.Message msg = (com.tmn_xos.platform.rabbitmq.api.pojo.Message) this.delegate.fromMessage(message);
        return msg;
    }

}
