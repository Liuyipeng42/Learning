package _6_.PublisherConfirm.IndividualConfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Producer {
    private final static String QUEUE_NAME = "publisher confirm";

    public static void main(String[] args) throws Exception {

        Channel channel = MQChannel.getChannel();

        // 开启发布确认
        channel.confirmSelect();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message;
        boolean success;

        long start = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++) {

            message = "Message " + i;

            channel.basicPublish(
                    "", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8)
            );

            // 每发送一个消息就停止发送消息，直到发送出去的消息得到确认才开始发送下一个消息，速度较慢，但可以明确的知道哪个消息出错
            success = channel.waitForConfirms();

            if (success){
                System.out.println(message + " success!");
            }

        }

        System.out.println("Cost " + (System.currentTimeMillis() - start) + " ms");

    }
}
