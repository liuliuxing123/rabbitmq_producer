package com.tmn_xos.platform.rabbitmq.common.serializer;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
public interface SerializerFactory {

    /**
     *
     * @return
     */
    Serializer create();

}
