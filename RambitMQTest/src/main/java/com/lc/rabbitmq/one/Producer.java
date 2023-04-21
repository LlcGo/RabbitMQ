package com.lc.rabbitmq.one;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/20
 * @Description
 * 生产者
 */
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        /**
         * 生成一个队列
         * 1.队列名称
         * 2.队列中的消息是否持久化（磁盘）默认消息存储在内存中
         * 3.该队列是否只供一个消费者消费，是否进行消息共享，true可以多个消费者消费
         * 4.是否自动删除，最后一个消费者端开连接后 该队列是否自动删除 true自动删除
         * 5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        //发消息
        String message = "hello world";

        /**
         * 发送一个消息
         * 1.发送到哪个交换机
         * 2.路由的key值  本次是队列名称
         * 3.其他参数信息
         * 4.发送消息的消息体
         */
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发送完毕");
    }
}
