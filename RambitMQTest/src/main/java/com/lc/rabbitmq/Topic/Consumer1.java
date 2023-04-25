package com.lc.rabbitmq.Topic;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.lc.rabbitmq.utils.Constant.TOPIC_NAME;

/**
 * @Author Lc
 * @Date 2023/4/25
 * @Description
 */
public class Consumer1 {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //设置主题交换机
        channel.exchangeDeclare(TOPIC_NAME,"topic");
        //设置队列
        String queue = "Top1";
        channel.queueDeclare(queue,false,false,false,null);
        //队列与交换机绑定
        channel.queueBind(queue,TOPIC_NAME,"lazy.#");
        //接收消息
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("消费者Top1收到的消息是" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        CancelCallback cancelCallback = (consumerTag) ->{

        };
        channel.basicConsume(queue,true,deliverCallback,cancelCallback);

    }
}
