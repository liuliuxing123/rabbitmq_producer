package com.tmn_xos.platform.rabbitmq.producer.broker;

import com.google.common.collect.Lists;
import com.tmn_xos.platform.rabbitmq.api.pojo.Message;

import java.util.List;

/**
 * @Author: liuxing
 * @Description: threadLocal
 * @Date: 2020/7/2
 */
public class MessageHolder {

    private List<Message> messages = Lists.newArrayList();

    private static final ThreadLocal<MessageHolder> HOLDER = ThreadLocal.withInitial(MessageHolder::new);

    public static void add(Message message) {
        HOLDER.get().messages.add(message);
    }

    public static List<Message> getAndClear() {
        List<Message> tmp = Lists.newArrayList(HOLDER.get().messages);
        HOLDER.remove();
        return tmp;
    }

}
