package com.tmn_xos.platform.rabbitmq.api;

import java.util.List;

import com.tmn_xos.platform.rabbitmq.api.exception.MessageRunTimeException;
import com.tmn_xos.platform.rabbitmq.api.pojo.Message;

/**
 * @Author: liuxing
 * @Description: プロデューサー
 * @Date: 2021/06/30
 */
public interface MessageProducer {

    /**
     * 送信
     * @param message
     * @throws MessageRunTimeException
     */
    void send(Message message) throws MessageRunTimeException;

    /**
     * 一括送信
     * @param messages
     * @throws MessageRunTimeException
     */
    void send(List<Message> messages) throws MessageRunTimeException;

    /**
     * 送信SendCallback確認
     * @param message
     * @param sendCallback
     * @throws MessageRunTimeException
     */
    void send(Message message, SendCallback sendCallback) throws MessageRunTimeException;

}
