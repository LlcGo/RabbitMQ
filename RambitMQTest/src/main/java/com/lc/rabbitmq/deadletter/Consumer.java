package com.lc.rabbitmq.deadletter;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import static com.lc.rabbitmq.utils.Constant.DEAD_LINE;
import static com.lc.rabbitmq.utils.Constant.DEAD_QUEUE;

/**
 * @Author Lc
 * @Date 2023/4/26
 * @Description
 */
public class Consumer {
    public final static String Dead = "sixinlie";
    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //正常交换机 与 死信交换机
        channel.exchangeDeclare(DEAD_LINE,"direct");
        channel.exchangeDeclare(Dead,"direct");
        //设置死信交换机
        HashMap<String, Object> argument = new HashMap<>();
        //也可以设置过期时间但是不推荐没有生产者那边发送的灵活
        //设值私信交换机名字
        argument.put("x-dead-letter-exchange",Dead);
        //路由key
        argument.put("x-dead-letter-routing-key","lisi");
        //正常交换机队列与死信队列
        //设置队列最多只能容纳6个
        argument.put("x-max-length",6);
        channel.queueDeclare(DEAD_QUEUE ,false,false,false,argument);
        channel.queueDeclare("sixinduilie",false,false,false,null);
        //普通交换机绑定交换机
        channel.queueBind(DEAD_QUEUE,DEAD_LINE,"zhangsan");
        //死信绑定交换机
        channel.queueBind("sixinduilie",Dead,"lisi");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            if(new String(message.getBody()).equals("11")){
                System.out.println("消息" + new String(message.getBody()) + "被拒绝接收" );
                channel.basicReject(message.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("消费者COM收到的消息是" + new String(message.getBody(), StandardCharsets.UTF_8));
                channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
            }
        };
        CancelCallback cancelCallback = (consumerTag) ->{

        };

        channel.basicConsume(DEAD_QUEUE,false,deliverCallback,cancelCallback);
    }
}
