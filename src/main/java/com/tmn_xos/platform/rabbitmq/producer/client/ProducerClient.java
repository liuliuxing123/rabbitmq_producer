package com.tmn_xos.platform.rabbitmq.producer.client;

import com.google.common.base.Preconditions;
import com.tmn_xos.platform.rabbitmq.api.MessageProducer;
import com.tmn_xos.platform.rabbitmq.api.SendCallback;
import com.tmn_xos.platform.rabbitmq.api.common.MessageType;
import com.tmn_xos.platform.rabbitmq.api.exception.MessageRunTimeException;
import com.tmn_xos.platform.rabbitmq.api.pojo.Message;
import com.tmn_xos.platform.rabbitmq.producer.broker.MessageHolder;
import com.tmn_xos.platform.rabbitmq.producer.broker.RabbitBroker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
@Component
public class ProducerClient implements MessageProducer {

    @Autowired
    private RabbitBroker rabbitBroker;

    @Override
    public void send(Message message) throws MessageRunTimeException {
        // チェックtopic
        Preconditions.checkNotNull(message.getTopic());
        // メッセージタイプ
        String messageType = message.getMessageType();
        switch (messageType) {
            case MessageType.RAPID:
                rabbitBroker.rapidSend(message);
                break;
            case MessageType.CONFIRM:
                rabbitBroker.confirmSend(message);
                break;
            case MessageType.RELIANT:
                rabbitBroker.reliantSend(message);
                break;
            default:
                break;
        }
    }

    @Override
    public void send(List<Message> messages) throws MessageRunTimeException {
        messages.forEach( message -> {
            // DBに登録しないメッセージ
            message.setMessageType(MessageType.RAPID);
            MessageHolder.add(message);
        });
        rabbitBroker.sendMessages();
    }

    @Override
    public void send(Message message, SendCallback sendCallback) throws MessageRunTimeException {

    }

}
