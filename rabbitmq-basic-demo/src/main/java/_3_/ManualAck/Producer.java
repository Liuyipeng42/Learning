package _3_.ManualAck;

import com.rabbitmq.client.Channel;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Producer {
    private final static String QUEUE_NAME = "manual ack";

    public static void main(String[] args) throws Exception {

        Channel channel = MQChannel.getChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String message;

        Scanner scanner = new Scanner(System.in);

        while (true){

            message = scanner.next();
            if (message.equals("quit"))
                break;

            // 使用 utf-8 防止中文出现乱码
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("消息发送完毕");
        }

    }
}
