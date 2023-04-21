package com.lc.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Author Lc
 * @Date 2023/4/20
 * @Description
 */
public class RabbitMQConnectionFactory {
    public static final String URL = "192.168.190.100";
    public static final String USER_NAME = "root";
    public static final String PASS_WORD = "root";

    public static Channel getConnection(){
        //创建一个工厂
        ConnectionFactory factory = new ConnectionFactory();
        //工厂ip 连接RabbitMQ的队列
        factory.setHost(URL);
        //用户名
        factory.setUsername(USER_NAME);
        //密码
        factory.setPassword(PASS_WORD);
        Channel channel = null;
        try {
            //创建连接
            Connection connection = factory.newConnection();
            //获取信道
            channel = connection.createChannel();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }
}
