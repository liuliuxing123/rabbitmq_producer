package com.tmn_xos.platform.rabbitmq.producer.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */


@ComponentScan(value = "com.tmn_xos.platform.rabbitmq.*")
@Configuration
public class RabbitProducerAutoConfiguration {
}
