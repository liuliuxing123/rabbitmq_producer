package com.tmn_xos.platform.rabbitmq.producer.broker;

import com.tmn_xos.platform.rabbitmq.api.pojo.Message;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
public interface RabbitBroker {

    /**
     * 快速メッセージ
     * @param message
     */
    void rapidSend(Message message);

    /**
     * 確認必要メッセージ
     * @param message
     */
    void confirmSend(Message message);

    /**
     * 妥当性必要メッセージ（DBに登録）
     * @param message
     */
    void reliantSend(Message message);

    /**
     * 一括メッセージ
     */
    void sendMessages();

}
