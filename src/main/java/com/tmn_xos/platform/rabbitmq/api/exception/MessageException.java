package com.tmn_xos.platform.rabbitmq.api.exception;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2021/06/30
 */
public class MessageException extends Exception {

    private static final long serialVersionUID = -3793852268630770174L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

}
