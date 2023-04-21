package com.lc.rabbitmq.two;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;

import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/21
 * @Description 可以发送大量的消息
 */
public class Task01 {

    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();

        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //从控制台当中接收信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成" + message);
        }
    }
}
