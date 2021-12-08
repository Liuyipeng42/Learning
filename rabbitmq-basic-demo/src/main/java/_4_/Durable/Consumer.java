package _4_.Durable;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private final static String QUEUE_NAME = "durable";

    public static void main(String[] args) {

        new Thread(new Consume(QUEUE_NAME, "Thread 1", 1)).start();

        new Thread(new Consume(QUEUE_NAME, "Thread 2", 5)).start();
    }
}


class Consume extends Thread{

    private final String threadName;
    private final String queueName;
    public int sleepTime;

    public Consume(String queueName, String threadName, int sleepTime){
        this.threadName = threadName;
        this.queueName = queueName;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run(){

        CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息消费被中断");

        try {
            Channel channel = MQChannel.getChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());

                // 模拟消息的处理花费的时间
                try {
                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(threadName + ": " + message);

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };

            System.out.println(threadName + " 等待接收消息.........");
            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }


}
