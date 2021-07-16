package com.tmn_xos.platform.rabbitmq.producer.broker.queue;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
@Configuration
@ConfigurationProperties(prefix = "thread.pool")
public class ThreadPool {

    /**
     * 
     */
    private String threadPoolName;
    /**
     * スレッド数
     */
    private Integer coreThreadSize;

    /**
     * 最大スレッド
     */
    private Integer maxThreadSize;

    /**
     * 生きてるスレッド数
     */
    private Long keepAliveTime;

    /**
     *　容量
     */
    private Integer capacity;

	public String getThreadPoolName() {
		return threadPoolName;
	}

	public void setThreadPoolName(String threadPoolName) {
		this.threadPoolName = threadPoolName;
	}

	public Integer getCoreThreadSize() {
		return coreThreadSize;
	}

	public void setCoreThreadSize(Integer coreThreadSize) {
		this.coreThreadSize = coreThreadSize;
	}

	public Integer getMaxThreadSize() {
		return maxThreadSize;
	}

	public void setMaxThreadSize(Integer maxThreadSize) {
		this.maxThreadSize = maxThreadSize;
	}

	public Long getKeepAliveTime() {
		return keepAliveTime;
	}

	public void setKeepAliveTime(Long keepAliveTime) {
		this.keepAliveTime = keepAliveTime;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
    
    

}
