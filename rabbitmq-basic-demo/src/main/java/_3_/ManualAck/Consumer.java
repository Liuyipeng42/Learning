package _3_.ManualAck;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    private final static String QUEUE_NAME = "manual ack";

    public static void main(String[] args) {

        new Thread(new Consume(QUEUE_NAME, "Thread 1", 2)).start();

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

                // 模拟处理消息时出错，然后将消息返回队列，交给另一个消费者
                if (sleepTime > 3){
                    throw new RuntimeException();
                }

                System.out.println(threadName + ": " + message);

                // 确认成功消费
                // 1.消息标记 tag
                // 2.是否批量应答未应答消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

                // 拒绝消费此消息
                // 1.消息标记 tag
                // 2.是否批量应答未应答消息
                // 3.是否放回队列中，如果为 false，就不会放回队列，此消息就会被队列删除
//                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, true);
            };

            System.out.println(threadName + " 等待接收消息.........");

            // 将 第二个 参数设为 false，表明不使用自动应答
            // 若开启自动应答，则队列会在发送消息给消费者后直接删除消息，
            // 若消息的处理出错，就会丢失消息，所以要使用手动应答
            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }


}
