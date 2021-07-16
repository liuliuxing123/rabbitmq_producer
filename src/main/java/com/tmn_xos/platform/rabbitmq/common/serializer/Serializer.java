package com.tmn_xos.platform.rabbitmq.common.serializer;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
public interface Serializer {

    /**
     * 
     * @param data
     * @return
     */
    byte[] serializeRaw(Object data);

    /**
     * String
     * @param data
     * @return
     */
    String serialize(Object data);

    /**
     * 
     * @param content
     * @param <T>
     * @return
     */
    <T> T deserialize(String content);

    /**
     * 
     * @param content
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] content);

}
