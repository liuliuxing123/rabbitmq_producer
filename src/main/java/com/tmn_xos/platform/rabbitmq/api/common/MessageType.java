package com.tmn_xos.platform.rabbitmq.api.common;

/**
 * @Author: liuxing
 * @Description: メッセージタイプ
 * @Date: 2021/06/30
 */
public class MessageType {

    /**
     * 	rabbitmqに正常送信confirm必要ないメッセージ
     */
    public static final String RAPID = "0";

    /**
     * 	rabbitmqに正常送信confirm必要、DBに登録しない
     */
    public static final String CONFIRM = "1";

    /**
     * 	DBに登録して、rabbitmqに正常送信confirm必要
     * 	PS: 結果整合性
     */
    public static final String RELIANT = "2";

}
