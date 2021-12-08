package _4_.Durable;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Producer {
    private final static String QUEUE_NAME = "durable";

    public static void main(String[] args) throws Exception {

        Channel channel = MQChannel.getChannel();

        // 第二个设置为 true 表明 进行队列的持久化，
        // 队列持久化只能保证队列的持久化。队列中没有发出的消息不能持久化
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message;

        Scanner scanner = new Scanner(System.in);

        while (true) {

            message = scanner.next();
            if (message.equals("quit"))
                break;

            // 使用 utf-8 防止中文出现乱码
            // 将第三个参数设为 MessageProperties.PERSISTENT_TEXT_PLAIN 就可以实现消息的 持久化
            // 单只设置消息的持久化，重启之后队列消失，即使设置了消息持久化，队列中原有的消息也会丢失
            channel.basicPublish(
                    "", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println("消息发送完毕");
        }

    }
}
