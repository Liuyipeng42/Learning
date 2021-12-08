package com.rabbitmq.demo.Consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Date;

@Component
public class QBConsumer {

    // 指定一个 Listener 的 containerFactory， 进行相应的设置
    @RabbitListener(queues = "QB", ackMode = "MANUAL", containerFactory = "containerFactory")
    public void QBConsumer1(Message message, Channel channel) throws IOException, InterruptedException {

        Thread.sleep(2000);
        String msg = new String(message.getBody());
        System.out.println("当前时间：" + new Date() + " , 队列 QB 的消费者 1 收到信息：" + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "QB", ackMode = "MANUAL", containerFactory = "containerFactory")
    public void QBConsumer2(Message message, Channel channel) throws IOException, InterruptedException {

        Thread.sleep(2000);
        String msg = new String(message.getBody());
        System.out.println("当前时间：" + new Date() + " , 队列 QB 的消费者 2 收到信息：" + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
