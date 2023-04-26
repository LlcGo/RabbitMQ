package com.lc.rabbitmq.deadletter;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.lc.rabbitmq.utils.Constant.DEAD_LINE;
import static com.lc.rabbitmq.utils.Constant.DEAD_QUEUE;

/**
 * @Author Lc
 * @Date 2023/4/26
 * @Description
 */
public class Consumer2 {
    public final static String Dead = "sixinlie";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("消费者COM收到的消息是" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        CancelCallback cancelCallback = (consumerTag) ->{

        };
        channel.basicConsume("sixinduilie",true,deliverCallback,cancelCallback);
    }
}
