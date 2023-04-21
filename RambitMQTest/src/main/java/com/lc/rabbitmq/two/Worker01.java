package com.lc.rabbitmq.two;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/21
 * @Description
 * 一个工作线程（消费者）
 */
public class Worker01 {
    //接收消息
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //消息接收
        DeliverCallback deliverCallback = (conTage,msg) ->{
            System.out.println("接收到的消息" + new String(msg.getBody()));
        };
        //消息接收被去取消时候
        CancelCallback cancelCallback = (cosTage) -> {
            System.out.println(cosTage + "消息者取消消息接口回调逻辑");
        };
        System.out.println("C2等待接收消息....");
        //接收消息
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
