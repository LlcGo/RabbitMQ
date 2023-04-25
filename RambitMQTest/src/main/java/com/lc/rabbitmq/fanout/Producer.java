package com.lc.rabbitmq.fanout;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.lc.rabbitmq.utils.Constant.EX_NAME;
import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/25
 * @Description
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();

        channel.exchangeDeclare(EX_NAME,"fanout");


        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(EX_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息完成" + message);
        }

    }
}
