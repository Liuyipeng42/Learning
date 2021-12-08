package _6_.PublisherConfirm;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private final static String QUEUE_NAME = "publisher confirm";

    public static void main(String[] args) {

        new Thread(new Consume(QUEUE_NAME, "Thread 1")).start();

        new Thread(new Consume(QUEUE_NAME, "Thread 2")).start();
    }
}


class Consume extends Thread{

    private final String threadName;
    private final String queueName;

    public Consume(String queueName, String threadName){
        this.threadName = threadName;
        this.queueName = queueName;
    }

    @Override
    public void run(){

        CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息消费被中断");

        try {
            Channel channel = MQChannel.getChannel();

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
