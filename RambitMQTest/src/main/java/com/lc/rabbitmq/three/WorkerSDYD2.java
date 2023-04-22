package com.lc.rabbitmq.three;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.lc.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/22
 * @Description
 */
public class WorkerSDYD2 {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        System.out.println("work2等待时间较长");
        //设置为手动应答
        boolean AutoAck = false;
        //接收的时候
        //接收的时候
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            SleepUtils.sleep(30);
            System.out.println("消费者2接收到消息" + new String(message.getBody(), StandardCharsets.UTF_8));
            /**
             * 1.消息标记tag
             * 2.是否批量未应答消息
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
        //消息被取消的时候
        CancelCallback cancelCallback = (consumerTag) ->{
            System.out.println(consumerTag+ "消费者2取消接口回调逻辑");
        };

        channel.basicConsume(QUEUE_NAME,AutoAck,deliverCallback,cancelCallback);
    }
}
