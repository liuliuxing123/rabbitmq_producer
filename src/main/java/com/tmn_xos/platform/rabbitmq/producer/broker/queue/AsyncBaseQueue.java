package com.tmn_xos.platform.rabbitmq.producer.broker.queue;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmn_xos.platform.rabbitmq.producer.broker.confirm.ConfirmCallback;

import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */
@Component
public class AsyncBaseQueue {
	private static final Logger log = LoggerFactory.getLogger(AsyncBaseQueue.class);
    @Autowired
    private ThreadPool threadPool;

    /**
     * 
     */
    private ExecutorService senderAsync = null;

    /**
     * 
     */
    @PostConstruct
    public void initSender(){
        senderAsync = new ThreadPoolExecutor(
                threadPool.getCoreThreadSize(),
                threadPool.getMaxThreadSize(),
                threadPool.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000),
                // 初始化 ThreadFactory
                runnable -> {
                    Thread thread = new Thread(runnable);
                    thread.setName(threadPool.getThreadPoolName());
                    return thread;
                },
                new MyAbortPolicy());
    }

    /**
     * 
     */
    private static class MyAbortPolicy implements RejectedExecutionHandler {

        MyAbortPolicy() {
        }

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.error("async sender is error rejected, runnable: {}, executor: {}", r, executor);
        }
    }

    /**
     * 非同期
     * @param runnable
     */
    public void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }

}
