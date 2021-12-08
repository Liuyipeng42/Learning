package com.rabbitmq.demo.Consumer;


import com.rabbitmq.demo.Entity.MyMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RabbitListener(queues = "QA", ackMode = "MANUAL")
public class QAConsumer {
    @RabbitHandler
    // 使用 @Payload 和 @Headers 注解可以获取消息中的 body 与 headers 信息
    public void consume(@Payload MyMessage message,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                        Channel channel) throws IOException {
        System.out.println("当前时间：" + new Date() + " , 队列 QA 的消费者收到信息：" + message.getMessage());
        channel.basicAck(tag, false);
    }

    @RabbitHandler
    // 使用 @Payload 和 @Headers 注解可以获取消息中的 body 与 headers 信息
    public void consume(@Payload byte[] message,
                        @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                        Channel channel) throws IOException {
        System.out.println("当前时间：" + new Date() + " , 队列 QA 的消费者收到信息：" + new String(message));
        channel.basicAck(tag, false);
    }

}
