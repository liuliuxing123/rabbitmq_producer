package com.tmn_xos.platform.rabbitmq.api;

import com.tmn_xos.platform.rabbitmq.api.pojo.Message;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2021/06/30
 */
public interface MessageListener {

    /**
     * 
     * @param message
     */
    void onMessage(Message message);

}
