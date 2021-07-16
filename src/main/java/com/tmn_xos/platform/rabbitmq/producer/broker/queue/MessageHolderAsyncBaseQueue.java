package com.tmn_xos.platform.rabbitmq.producer.broker.queue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.concurrent.*;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2020/6/27
 */

@Component
public class MessageHolderAsyncBaseQueue {
	private static final Logger logger = LoggerFactory.getLogger(MessageHolderAsyncBaseQueue.class);
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
                // 
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
        	logger.error("async sender is error rejected, runnable: {}, executor: {}", r, executor);
        }
    }

    /**
     * 
     * @param runnable
     */
    public void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }

	public ThreadPool getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPool threadPool) {
		this.threadPool = threadPool;
	}

	public ExecutorService getSenderAsync() {
		return senderAsync;
	}

	public void setSenderAsync(ExecutorService senderAsync) {
		this.senderAsync = senderAsync;
	}
    

}
