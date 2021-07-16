package com.tmn_xos.platform.rabbitmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmn_xos.platform.rabbitmq.api.common.MessageType;
import com.tmn_xos.platform.rabbitmq.api.pojo.Message;
import com.tmn_xos.platform.rabbitmq.producer.client.ProducerClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/30
 */
@RestController
@Slf4j
public class ProducerController {

    @Autowired
    private ProducerClient producerClient;

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${delay.exchange.name}")
    private String delayExchangeName;

    /**
     * demo
     */
    private AtomicInteger messageId = new AtomicInteger(200);

    /**
     * 送信demo
     * @return
     */
    @PostMapping("/produce")
    public String produceMessage(){
        String messageId = String.valueOf(this.messageId.incrementAndGet());
        Map<String,Object> attributes = new HashMap<>(2);
        attributes.put("name","user1");
        attributes.put("age","18");

        Message message =
                new Message(messageId,exchangeName,"routingKey.demo",attributes,0);
        // 
        message.setMessageType(MessageType.RELIANT);
        message.setDelayMills(5000);
        producerClient.send(message);

        return "ok";
    }

    /**
     * 一括送信demo
     * @return
     */
    @PostMapping("/produceMessages")
    public String produceMessages(){
        List<Message> messages = new ArrayList<Message>();

        for(int i=0;i<3;i++){
            String messageId = String.valueOf(this.messageId.incrementAndGet());
            Map<String,Object> attributes = new HashMap<>(2);
            attributes.put("name","user" + (i+1) + "さん");
            attributes.put("age",i);

            Message message =
                    new Message(messageId,exchangeName,"routingKey.demo",attributes,0);

            message.setDelayMills(5000);
            messages.add(message);
        }

        producerClient.send(messages);

        return "ok";
    }

    /**
     * 遅延送信demo
     * @return
     */
    @PostMapping("/produceDelayMessage")
    public String produceDelayMessage(){
        String messageId = String.valueOf(this.messageId.incrementAndGet());
        Map<String,Object> attributes = new HashMap<>(2);
        attributes.put("name","user1");
        attributes.put("age","18");

        Message message =
                new Message(messageId,delayExchangeName,"delay.demo",attributes,5000);
       
        message.setMessageType(MessageType.RELIANT);
        producerClient.send(message);

        return "ok";
    }


}
