package com.tmn_xos.platform.rabbitmq.api;

/**
 * @Author: liuxing
 * @Description: コールバック
 * @Date: 2021/06/30
 */
public interface SendCallback {

    /**
     * 送信成功コールバック
     */
    void onSuccess();

    /**
     * 送信失敗コールバック
     */
    void onFailure();

}
