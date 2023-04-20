package com.lc.rabbitmq.one;

import com.rabbitmq.client.*;


import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * @Author Lc
 * @Date 2023/4/20
 * @Description
 * 消费者 接收消息
 */
public class Consumer {
    //队列名称
    public static final String QUEUE_NAME = "hello";
    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //声明接收消息
        //String consumerTag, Delivery message
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println(new String(message.getBody()));
        };
        //String consumerTag
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费消息被中断时候执行");
        };
        //取消消息时候的回调
        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否 true自动应答 false 代表手动应答
         * 3.消费者没有消费成功的回调
         * 4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
