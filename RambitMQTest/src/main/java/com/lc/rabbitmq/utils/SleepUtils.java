package com.lc.rabbitmq.utils;

/**
 * @Author Lc
 * @Date 2023/4/22
 * @Description
 */
public class SleepUtils {
    public static void sleep(int second){
        try {
            Thread.sleep(1000*second);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}