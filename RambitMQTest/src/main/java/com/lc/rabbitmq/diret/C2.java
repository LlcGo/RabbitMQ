package com.lc.rabbitmq.diret;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.lc.rabbitmq.utils.Constant.DIR_NAME;

/**
 * @Author Lc
 * @Date 2023/4/25
 * @Description
 */
public class C2 {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //创建交换机
        channel.exchangeDeclare(DIR_NAME,"direct");
        //创建队列
        channel.queueDeclare("disk",false,false,false,null);
        //绑定俩个
        channel.queueBind("disk",DIR_NAME,"error");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("消费者C2收到的消息是" + new String(message.getBody(), StandardCharsets.UTF_8));
        };
        CancelCallback cancelCallback = (consumerTag) ->{

        };
        channel.basicConsume("disk",true,deliverCallback,cancelCallback);

    }
}
