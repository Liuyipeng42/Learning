package _5_.FairDispatch;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private final static String QUEUE_NAME = "durable";

    public static void main(String[] args) {

        new Thread(new Consume(QUEUE_NAME, "Thread 1", 0)).start();

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

            // 通过此方法可以设置 不公平分发，参数是 此信道中可以存放的消息数
            // 若一个信道中缓冲的消息数（包括正在处理的消息）少于此 参数，即使没有处理完这些消息，也会给这个信道发放消息，缓冲到信道中
            // 若一个信道中缓冲的消息数等于此 参数，就不会给这个信道发放消息，会把消息发送给其他没有缓冲满的信道
            // 当此值设置为 1 时，表明每个信道只能缓冲一个消息，即正在处理的消息，
            // 正好说明此信道正在处理其他消息，所以此时就只会给其他空闲的信道发送消息，
            channel.basicQos(1);
            System.out.println(threadName + " 等待接收消息.........");
            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

}
