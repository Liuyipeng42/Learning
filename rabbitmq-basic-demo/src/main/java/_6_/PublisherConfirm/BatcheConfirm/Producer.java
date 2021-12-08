package _6_.PublisherConfirm.BatcheConfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;

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

            // 每发送 100 个消息确认一次，比单个确认速度快，但如果出错就不能知道那个消息出错
            if (i % 100 == 0) {
                success = channel.waitForConfirms();
                if (success){
                    System.out.println(message + " success!");
                }
            }

        }

        System.out.println("Cost " + (System.currentTimeMillis() - start) + " ms");

    }
}
