package com.lc.rabbitmq.deadletter;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.lc.rabbitmq.utils.Constant.DEAD_LINE;
import static com.lc.rabbitmq.utils.Constant.DIR_NAME;

/**
 * @Author Lc
 * @Date 2023/4/26
 * @Description
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        channel.exchangeDeclare(DEAD_LINE,"direct");

        //设置时间
//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();

        for (int i = 0; i < 10; i++) {
            channel.basicPublish(DEAD_LINE,"zhangsan",null,(i+"1").getBytes());
        }

    }
}

