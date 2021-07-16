package com.tmn_xos.platform.rabbitmq.producer.broker.confirm;

import cn.hutool.core.util.StrUtil;

import com.google.common.base.Splitter;
import com.tmn_xos.platform.rabbitmq.api.common.MessageType;
import com.tmn_xos.platform.rabbitmq.domain.BrokerMessage;
import com.tmn_xos.platform.rabbitmq.producer.constant.BrokerMessageStatus;
import com.tmn_xos.platform.rabbitmq.repository.BrokerMessageDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: liuxing
 * @Description: RabbitMQ　ack
 * @Date: 2020/7/20
 */
@Component
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
	private static final Logger log = LoggerFactory.getLogger(ConfirmCallback.class);
    @Autowired
    private BrokerMessageDao brokerMessageDao;

    /**
     * guava splitter：
     */
    private Splitter splitter = Splitter.on("#");

    /**
     * 
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData.getId();
        if(StrUtil.isBlank(id)){
            log.error("send message content is empty");
            return ;
        }
        // 	
        List<String> strings = splitter.splitToList(id);
        String messageId = strings.get(0);
        long sendTime = Long.parseLong(strings.get(1));
        String messageType = strings.get(2);
        if(ack) {
            //	Broker ACK成功場合,  SEND_OKの状態を更新

            // 	reliant の場合、DBに更新
            if(MessageType.RELIANT.endsWith(messageType)) {
                BrokerMessage bm = new BrokerMessage();
                bm.setMessageId(messageId);
                bm.setStatus(BrokerMessageStatus.SEND_OK.getStatus());
                bm.setUpdateTime(new Date());
                brokerMessageDao.saveAndFlush(bm);
            }
            log.info("send message is OK, confirm messageId: {}, sendTime: {}", messageId, sendTime);
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", messageId, sendTime);
            // キューが空間ない/
        }
    }

}
