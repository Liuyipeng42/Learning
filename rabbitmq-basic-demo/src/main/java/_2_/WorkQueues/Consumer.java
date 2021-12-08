package _2_.WorkQueues;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) {

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody());
            System.out.println(consumerTag + ": " + message);
        };

        CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息消费被中断");

        Runnable action = () -> {
            try {
                Channel channel = MQChannel.getChannel();

                System.out.println("Thread1 等待接收消息.........");

                channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);

            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        };


        // 创建两个信道（工作线程），默认会以轮询的方式分发消息，即使目标线程正在处理其他消息
        new Thread(action).start();

        new Thread(action).start();

    }
}
