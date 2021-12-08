package _5_.FairDispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Producer {
    private final static String QUEUE_NAME = "durable";

    public static void main(String[] args) throws Exception {

        Channel channel = MQChannel.getChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message;

        Scanner scanner = new Scanner(System.in);

        while (true) {

            message = scanner.next();
            if (message.equals("quit"))
                break;

            channel.basicPublish(
                    "", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8)
            );
            System.out.println("消息发送完毕");
        }

    }
}
