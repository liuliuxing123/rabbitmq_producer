package com.tmn_xos.platform.rabbitmq.producer.broker.impl;

import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmn_xos.platform.rabbitmq.api.common.MessageType;
import com.tmn_xos.platform.rabbitmq.api.pojo.Message;
import com.tmn_xos.platform.rabbitmq.domain.BrokerMessage;
import com.tmn_xos.platform.rabbitmq.producer.broker.MessageHolder;
import com.tmn_xos.platform.rabbitmq.producer.broker.RabbitBroker;
import com.tmn_xos.platform.rabbitmq.producer.broker.container.RabbitTemplateContainer;
import com.tmn_xos.platform.rabbitmq.producer.broker.queue.AsyncBaseQueue;
import com.tmn_xos.platform.rabbitmq.producer.broker.queue.MessageHolderAsyncBaseQueue;
import com.tmn_xos.platform.rabbitmq.producer.constant.BrokerMessageConst;
import com.tmn_xos.platform.rabbitmq.producer.constant.BrokerMessageStatus;
import com.tmn_xos.platform.rabbitmq.repository.BrokerMessageDao;

import java.util.Date;
import java.util.List;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
@Component

public class RabbitBrokerImpl implements RabbitBroker {
	private static final Logger logger = LoggerFactory.getLogger(RabbitBrokerImpl.class);
    @Autowired
    private RabbitTemplateContainer rabbitTemplateContainer;
    @Autowired
    private AsyncBaseQueue asyncBaseQueue;
    @Autowired
    private MessageHolderAsyncBaseQueue messageHolderAsyncBaseQueue;
    @Autowired
    private BrokerMessageDao brokerMessageDao;

    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    /**
     * 	
     * @param message
     */
    private void sendKernel(Message message) {
        asyncBaseQueue.submit(() -> {
            // %s#%s#%s -> メッセージID#現在時間#メッセージタイプ
            CorrelationData correlationData =
                    new CorrelationData(String.format("%s#%s#%s",
                            message.getMessageId(),
                            System.currentTimeMillis(),
                            message.getMessageType()));
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();
            RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
            rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
            logger.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });
    }


    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliantSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        BrokerMessage bm = brokerMessageDao.findByMessageId(message.getMessageId());
        if(bm == null) {
            // 1.ログシステムに登録
        	logger.info("message: {}", JSONUtil.toJsonStr(message));
            Date now = new Date();
            // tryCount 
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMessageStatus.SENDING.getStatus());
            // nextRetry1分一回,check status(nextRetryの時間でスキャン)
            brokerMessage.setNextRetry(DateUtils.addMinutes(now, BrokerMessageConst.TIMEOUT));
            brokerMessage.setCreateTime(now);
            brokerMessage.setUpdateTime(now);
            brokerMessage.setMessage(JSONUtil.toJsonStr(message));
            brokerMessageDao.save(brokerMessage);
        }
        //2. rabbitmqに送信
        sendKernel(message);
    }

    @Override
    public void sendMessages() {
        List<Message> messages = MessageHolder.getAndClear();
        messages.forEach(message -> {
            messageHolderAsyncBaseQueue.submit(() -> {
                CorrelationData correlationData =
                        new CorrelationData(String.format("%s#%s#%s",
                                message.getMessageId(),
                                System.currentTimeMillis(),
                                message.getMessageType()));
                String topic = message.getTopic();
                String routingKey = message.getRoutingKey();
                RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
                rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
                logger.info("#RabbitBrokerImpl.sendMessages# send to rabbitmq, messageId: {}", message.getMessageId());
            });
        });
    }

}
