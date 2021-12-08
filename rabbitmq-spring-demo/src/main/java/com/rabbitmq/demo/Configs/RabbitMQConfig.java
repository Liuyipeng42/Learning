package com.rabbitmq.demo.Configs;


import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_A = "ExchangeA";
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String DEAD_LETTER_EXCHANGE = "DeadLetterExchange";
    public static final String DEAD_LETTER_QUEUE = "DeadLetterQueue";

    @Bean
    public RabbitTemplate rabbitTemplate() {
        // ConnectionFactory用于连接到RabbitMQ
        ConnectionFactory rabbitConnectionFactory = new ConnectionFactory();
        rabbitConnectionFactory.setHost("localhost");
        rabbitConnectionFactory.setPort(5672);
        rabbitConnectionFactory.setUsername("admin");
        rabbitConnectionFactory.setPassword("121522734a");

        CachingConnectionFactory cf = new CachingConnectionFactory(rabbitConnectionFactory);

        // correlated:
        //   发布消息成功到交换器后会触发回调方法
        //  simple:
        //      CORRELATED值一样会触发回调方法
        //      在发布消息成功后使用rabbitTemplate调用 waitForConfirms或 waitForConfirmsOrDie方法等待broker节点返回发送结果
        cf.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cf);

        // 消息发送给消费者失败后，重新返回队列
        rabbitTemplate.setMandatory(true);

        // 发布确认
        // 指定 ConfirmCallback
        // ConfirmCallback只确认消息是否到达exchange，已实现方法confirm中ack属性为标准，true到达
        rabbitTemplate.setConfirmCallback(
                (correlationData, ack, cause) -> {
                    if(ack){
                        System.out.println("消息发送成功");
                    }else {
                        System.out.println("消息唯一标识：" + correlationData + " 确认结果：" + ack + " 失败原因：" + cause);
                    }
                }
        );
        // 指定 ReturnCallback
        // ReturnCallback消息没有正确到达队列时触发回调，如果正确到达队列不执行。
        rabbitTemplate.setReturnsCallback(
                (message) -> System.out.println("消息: " + message)
        );

        // 配置一个 MessageConverter 用于传输 自定义的 java对象
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }


    // MessageListener
    // MessageListener 是 Spring AMQP 异步消息投递的监听器接口，它只有一个方法onMessage，
    // 用于处理消息队列推送来的消息，作用类似于 Java API 中的 Consumer。
    //
    // MessageListenerContainer
    // MessageListenerContainer可以理解为MessageListener的容器，一个Container只有一个 Listener，
    // 但是可以生成多个线程使用相同的 MessageListener 同时消费消息。
    // SimpleRabbitListenerContainerFactory 可以生成 MessageListenerContainer
    // Container 可以管理 Listener 的生命周期，可以用于对于消费者进行配置。
    @Bean("containerFactory")
    public SimpleRabbitListenerContainerFactory myContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            org.springframework.amqp.rabbit.connection.ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        // 设置 消费者 的 缓冲的消息 的数量
        containerFactory.setPrefetchCount(1);
        configurer.configure(containerFactory, connectionFactory);
        return containerFactory;
    }

    // 声明 ExchangeA
    @Bean("ExchangeA")
    public DirectExchange xExchange() {
        return ExchangeBuilder.directExchange(EXCHANGE_A).durable(true).build();
    }

    // 声明队列 A ttl 为 5s 并绑定到对应的死信交换机
    @Bean("QA")
    public Queue queueA() {
        HashMap<String, Object> args = new HashMap<>(3);
        // 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 队列存活时间
        args.put("x-expires", 1800000);
        // 声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "DLRK");
        // 声明队列的 TTL
        args.put("x-message-ttl", 2000);
        // 设置队列中可以存储处于ready状态消息的数量
        args.put("x-max-length", 1);
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }

    // 声明队列 A 绑定 xExchange 交换机，Routing Key 为 XA
    @Bean
    public Binding queueABindingX(@Qualifier("QA") Queue queueA,
                                  @Qualifier("ExchangeA") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("RKA");
    }

    // 声明队列 B ttl 为 10s 并绑定到对应的死信交换机
    @Bean("QB")
    public Queue queueB() {
        HashMap<String, Object> args = new HashMap<>(3);
        // 声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        // 队列存活时间
        args.put("x-expires", 1800000);
        // 声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "DLRK");
        // 声明队列的 TTL
        args.put("x-message-ttl", 4000);
        // 设置队列中可以存储处于ready状态消息的数量
        args.put("x-max-length", 1);
        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }

    // 声明队列 B 绑定 xExchange 交换机，Routing Key 为 XB
    @Bean
    public Binding queueBBindingX(@Qualifier("QB") Queue queue1B,
                                  @Qualifier("ExchangeA") DirectExchange xExchange) {
        return BindingBuilder.bind(queue1B).to(xExchange).with("RKB");
    }

    // 声明 DeadLetterExchange 死信交换机
    @Bean("DeadLetterExchange")
    public DirectExchange yExchange() {
        return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    // 声明死信队列 QD
    @Bean("DeadLetterQueue")
    public Queue queueD() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    // 声明死信队列 QD 绑定关系
    @Bean
    public Binding deadLetterBindingQAD(@Qualifier("DeadLetterQueue") Queue DeadLetterQueue,
                                        @Qualifier("DeadLetterExchange") DirectExchange DeadLetterExchange) {
        return BindingBuilder.bind(DeadLetterQueue).to(DeadLetterExchange).with("DLRK");
    }

}
