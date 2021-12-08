package _2_.WorkQueues;

import com.rabbitmq.client.Channel;
import utils.MQChannel;

import java.util.Scanner;

public class Producer {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        Channel channel = MQChannel.getChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message;

        Scanner scanner = new Scanner(System.in);

        while (true){

            message = scanner.next();
            if (message.equals("quit"))
                break;

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("消息发送完毕");
        }

    }
}
