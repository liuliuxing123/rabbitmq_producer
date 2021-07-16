package com.tmn_xos.platform.rabbitmq.api.pojo;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tmn_xos.platform.rabbitmq.api.common.MessageType;

import lombok.Data;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2021/06/30
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * ユニークID
     */
    private String messageId;

    /**
     * トピック
     */
    private String topic;

    /**
     * ルーティングキー
     */
    private String routingKey = "";

    /**
     * 付加メッセージ
     */
    private Map<String, Object> attributes = new HashMap<>();

    /**
     * 遅延送信タイム
     */
    private int delayMills = 10000;

    /**
     * 送信タイプ：confirmタイプ
     */
    private String messageType = MessageType.CONFIRM;

    public Message() {
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
    }

    public Message(String messageId, String topic, String routingKey, Map<String, Object> attributes, int delayMills,
                   String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.attributes = attributes;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }
    
    public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String routingKey) {
		this.routingKey = routingKey;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public int getDelayMills() {
		return delayMills;
	}

	public void setDelayMills(int delayMills) {
		this.delayMills = delayMills;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}
