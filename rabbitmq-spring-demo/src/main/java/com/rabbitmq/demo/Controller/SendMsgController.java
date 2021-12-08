package com.rabbitmq.demo.Controller;


import com.rabbitmq.demo.Entity.MyMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SendMsgController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public SendMsgController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable String message) {

        // convertAndSend 中传递的消息如果不是 org.springframework.amqp.core.Message 对象，则默认支持消息的持久化
        for (int i = 1; i <= 2; i++) {
            rabbitTemplate.convertAndSend(
                    "ExchangeA", "RKA", new MyMessage(message), new CorrelationData(String.valueOf(i))
            );
        }

        for (int i = 3; i <= 4; i++) {
            rabbitTemplate.convertAndSend(
                    "ExchangeA", "RKA", message, new CorrelationData(String.valueOf(i))
            );
        }

        for (int i = 5; i <= 8; i++) {
            rabbitTemplate.convertAndSend(
                    "ExchangeA", "RKB", message,
                    new CorrelationData(String.valueOf(i))
            );
        }
    }

}
