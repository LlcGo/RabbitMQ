package com.lc.rabbitmq.three;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/22
 * @Description
 */
public class Producer {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //开启发布确认
        channel.confirmSelect();
        //队列持久化
        boolean durable = true;
        //创建队列
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        //发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            //消息持久化
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息完成" + message);
        }
    }
}
