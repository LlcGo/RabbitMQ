package com.lc.rabbitmq.three;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;

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

        //创建队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息完成" + message);
        }
    }
}
