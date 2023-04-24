package com.lc.rabbitmq.four;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import static com.lc.rabbitmq.utils.Constant.QUEUE_NAME;

/**
 * @Author Lc
 * @Date 2023/4/24
 * @Description
 */
public class ConfirmMessage {
    public static void main(String[] args) throws Exception {
        //单个发布
//        SinglePublication(); //发布1000个消息花了596m
//        publishMessageBatch(); //发布1000批量个消息花了43m
        publishMessageAsync(); //发布1000个异步消息花了24ms
    }

    //单个
    private static void SinglePublication() throws IOException, InterruptedException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //发布确认
        channel.confirmSelect();
        //设置队列持久化
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        int n = 1000;
        long start = System.currentTimeMillis();
        while (n > 0){
            //消息持久化
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,String.valueOf(n).getBytes());
            System.out.println("生产者发送消息");
            n--;
            boolean flag = channel.waitForConfirms();
            if(flag){
                System.out.println("队列接收成功");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + 1000 +"个消息花了" + (end - start) + "m" );
    }

    //批量
    public static void  publishMessageBatch() throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //发布确认
        channel.confirmSelect();
        //设置队列持久化
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        int n = 1000;
        long start = System.currentTimeMillis();
        while (n > 0){

            channel.basicPublish("",QUEUE_NAME, null,String.valueOf(n).getBytes());
            System.out.println("生产者发送消息");
            n--;
            if(n == 0){
                boolean flag = channel.waitForConfirms();
                if(flag){
                    System.out.println("队列接收成功");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + 1000 +"个消息花了" + (end - start) + "m" );
    }

    //异步
    public static void  publishMessageAsync() throws Exception {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        //发布确认
        channel.confirmSelect();
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);
        int n = 1000;
        long start = System.currentTimeMillis();
        /**
         * 线程安全有序的哈希表，适用于高并发
         * 1.轻松的将序号与消息进行关联
         * 2.轻松批量删除条目，只要给序号
         * 3.支持并发访问
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirms
                                     = new ConcurrentSkipListMap<>();

        /**
         * 确认消息的回调
         * 1.消息序号
         * 2.true 可以确认小于等于当前序列号的消息
         *   false 确认当前序列号消息
         */
        //消息成功
        ConfirmCallback ackCallback = (sequenceNumber, multiple) ->{
            //如果是批量确认删除
            if(multiple){
               //返回的是小于当前序列号的未确认的序列
                ConcurrentNavigableMap<Long, String> confirmed
                        =  outstandingConfirms.headMap(sequenceNumber,true);
                System.out.println("确认的消息" + sequenceNumber);
                //清除该部分未确认消息
                confirmed.clear();
            }else {
                //单个删除
                //只清除当前的序列号的消息
                outstandingConfirms.remove(sequenceNumber);
            }
        };
        //消息失败
        ConfirmCallback nackCallback = (sequenceNumber, multiple) -> {
            String message = outstandingConfirms.get(sequenceNumber);
            System.out.println("发布消息" + message + "没有被确认,序号" + sequenceNumber);
        };

        //设置监听器
        /**
         * 1.确认收到消息的回调
         * 2.未收到消息的回调
         */
        channel.addConfirmListener(ackCallback,nackCallback);//异步
        while (n > 0){
            channel.basicPublish("",QUEUE_NAME, null,String.valueOf(n).getBytes());
            outstandingConfirms.put(channel.getNextPublishSeqNo(), String.valueOf(n));
//            System.out.println("生产者发送消息");
            n--;
        }
        long end = System.currentTimeMillis();
        System.out.println("发布" + 1000 +"个异步消息花了" + (end - start) + "ms" );
    }
}
