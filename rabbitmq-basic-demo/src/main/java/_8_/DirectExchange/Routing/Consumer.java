package _8_.DirectExchange.Routing;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) {

        new Thread(
                new Consume(
                        "directRouting","queue1", new String[]{"routingOne"}, "Thread 1"
                        )
        ).start();

        new Thread(
                new Consume(
                        "directRouting", "queue2", new String[]{"routingTwo", "routingThree"}, "Thread 2"
                )
        ).start();
    }
}


class Consume extends Thread{

    private final String exchangeName;
    private final String queueName;
    private final String threadName;
    private final String[] routingKeys;

    public Consume(String exchangeName, String queueName, String[] routingKeys, String threadName){
        this.exchangeName = exchangeName;
        this.queueName = queueName;
        this.threadName = threadName;
        this.routingKeys = routingKeys;
    }

    @Override
    public void run(){

        CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息消费被中断");

        try {
            Channel channel = MQChannel.getChannel();

            // 创建一个交换机，与生产者中的重复，
            // 但若存在一个一样的交换机，RabbitMQ 并不会再重复创建，此创建操作是幂等的
            channel.exchangeDeclare(exchangeName, "direct");

            channel.queueDeclare(queueName, true, false, false, null);

            // 通过 routingKey 将指定的队列 与 指定的交换机 绑定
            for (String routingKey: routingKeys) {
                channel.queueBind(queueName, exchangeName, routingKey);
            }

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());

                System.out.println(threadName + ": " + message);

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };

            channel.basicQos(1);
            System.out.println(threadName + " 等待接收消息.........");

            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

}
