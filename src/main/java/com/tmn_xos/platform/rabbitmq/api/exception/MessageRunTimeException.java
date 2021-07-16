package com.tmn_xos.platform.rabbitmq.api.exception;

/**
 * @Author: liuxing
 * @Description: 
 * @Date: 2021/06/30
 */
public class MessageRunTimeException extends RuntimeException {

    private static final long serialVersionUID = -8304412475983955630L;

    public MessageRunTimeException() {
        super();
    }

    public MessageRunTimeException(String message) {
        super(message);
    }

    public MessageRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageRunTimeException(Throwable cause) {
        super(cause);
    }

}
