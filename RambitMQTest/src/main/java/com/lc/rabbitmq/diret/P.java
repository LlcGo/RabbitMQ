package com.lc.rabbitmq.diret;

import com.lc.rabbitmq.utils.RabbitMQConnectionFactory;
import com.rabbitmq.client.Channel;
import org.omg.CORBA.MARSHAL;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static com.lc.rabbitmq.utils.Constant.DIR_NAME;
import static com.lc.rabbitmq.utils.Constant.EX_NAME;

/**
 * @Author Lc
 * @Date 2023/4/25
 * @Description
 */
public class P {
    public static void main(String[] args) throws IOException {
        Channel channel = RabbitMQConnectionFactory.getConnection();
        channel.exchangeDeclare(DIR_NAME,"direct");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish(DIR_NAME,"error",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送消息完成" + message);
        }
    }
}
