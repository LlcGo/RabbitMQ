package com.lc.rabbitmq.Topic;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.lc.rabbitmq.utils.Constant.DIR_NAME;
import static com.lc.rabbitmq.utils.Constant.TOPIC_NAME;

/**
 * @Author Lc
 * @Date 2023/4/25
 * @Description
 */
public class P1 {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //生产者连接的交换机
        channel.exchangeDeclare(TOPIC_NAME,"topic");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(TOPIC_NAME,"lazy.lc.lc.cl",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息完成" + message);
        }
    }
}
