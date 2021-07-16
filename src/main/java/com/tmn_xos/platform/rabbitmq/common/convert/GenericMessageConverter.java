package com.tmn_xos.platform.rabbitmq.common.convert;

import com.google.common.base.Preconditions;
import com.tmn_xos.platform.rabbitmq.common.serializer.Serializer;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @Author: liuxing
 * @Description: シリアライザー
 * @Date: 2020/6/27
 */
public class GenericMessageConverter implements MessageConverter {

    private Serializer serializer;

    public GenericMessageConverter(Serializer serializer) {
        Preconditions.checkNotNull(serializer);
        this.serializer = serializer;
    }

    /**
     * amqp Message デシリアル -> カスタマイズmessage
     * @param message
     * @return
     * @throws MessageConversionException
     */
    @Override
    public Object fromMessage(org.springframework.amqp.core.Message message) throws MessageConversionException {
        return this.serializer.deserialize(message.getBody());
    }

    /**
     * カスタマイズMessage シリアル -> amqp Message
     * @param object
     * @param messageProperties
     * @return
     * @throws MessageConversionException
     */
    @Override
    public org.springframework.amqp.core.Message toMessage(Object object, MessageProperties messageProperties)
            throws MessageConversionException {
        return new org.springframework.amqp.core.Message(this.serializer.serializeRaw(object), messageProperties);
    }

}
