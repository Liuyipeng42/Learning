package utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQChannel {
    public static Channel getChannel() throws IOException, TimeoutException {
        //创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("admin");
        factory.setPassword("121522734a");

        // channel 实现了自动 close 接口 自动关闭 不需要显示关闭
        Connection connection = factory.newConnection();

        // 正常情况要县创建交换机，然后创建信道，但也可以只创建信道，然后使用默认交换机
        return connection.createChannel();
    }
}
