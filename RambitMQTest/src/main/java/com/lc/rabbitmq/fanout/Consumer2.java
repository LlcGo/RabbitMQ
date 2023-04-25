package com.lc.rabbitmq.fanout;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.lc.rabbitmq.utils.Constant.EX_NAME;

/**
 * @Author Lc
 * @Date 2023/4/25
 * @Description
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {

        Channel channel = RabbitMQConnectionFactory.getConnection();
        //设置交换机
        channel.exchangeDeclare(EX_NAME,"fanout");
        //交换机获得的随机队列名称
        String queue = channel.queueDeclare().getQueue();
        //交换机与队列绑定
        channel.queueBind(queue,EX_NAME,"");
        System.out.println("等待消息");
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("消费者2收到的消息是" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        CancelCallback cancelCallback = (consumerTag) ->{

        };
        channel.basicConsume(queue,true,deliverCallback,cancelCallback);
    }
}
