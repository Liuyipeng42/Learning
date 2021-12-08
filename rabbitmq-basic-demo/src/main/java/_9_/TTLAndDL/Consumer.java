package _9_.TTLAndDL;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import utils.MQChannel;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 生产者产生 3 个消息，
        // 第一个消息会正常送到 TTLExp 交换机处理 10 秒，然后被拒绝，之后送到死信交换机
        // 第二个消息被发布时，因为第一个消息还缓存在消息队列中，而队列的消息长度被设置为 1，所以，第三个消息进入队列，此消息被送到死信交换机。
        // 第三个消息，会先放到消息队列中，然后超过设置的 TTL（3秒），被送到死信交换机，
        new Thread(
                new Consume(
                        "TTLExp", "DLExchange", "DLRoutingKey", "TTLQueue",
                        new String[]{"routingOne", "routingTwo", "routingThree"})
        ).start();

        new Thread(
                new DLConsume(
                        "DLExchange", "DLQueue", "DLRoutingKey")
        ).start();

    }
}


class Consume extends Thread{

    private final String exchangeName;
    private final String DLExchangeName;
    private final String DLRoutingKey;
    private final String queueName;
    private final String[] routingKeys;

    public Consume(String exchangeName, String dlExchangeName, String dlRoutingKey, String queueName, String[] routingKeys){
        this.exchangeName = exchangeName;
        this.DLExchangeName = dlExchangeName;
        DLRoutingKey = dlRoutingKey;
        this.queueName = queueName;
        this.routingKeys = routingKeys;
    }

    @Override
    public void run(){

        try {
            Channel channel = MQChannel.getChannel();
            channel.exchangeDeclare(exchangeName, "direct");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());

                // 模拟处理了 10 秒的消息
                // 当此值大于 每个消息的 TTL (3秒)时，会被送到 死信交换机
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 拒绝一个消息，此消息会被送到 死信交换机
                System.out.println(queueName + ": " + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

            };

            CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息消费被中断");

            HashMap<String, Object> args = new HashMap<>();
            // 在队列层次上设定消息存活时间，进入此队列的消息的存活时间都为此设置的值
            // 消息是否过期是在即将投递到消费者之前判定的，如果当前队列有严重的消息积压情况，则已过期的消息也许还能存活较长时间
            args.put("x-message-ttl", 5000);
            // 设置一个队列的存活时间，若此队列在设置的时间之内没有被使用就会被销毁
            args.put("x-expires", 1800000);
            // 为此队列绑定一个死信交换机
            args.put("x-dead-letter-exchange", DLExchangeName);
            // 为此队列生成的死信消息设置 Routing Key
            args.put("x-dead-letter-routing-key", DLRoutingKey);
            // 队列中的最大消息数，若队列中的消息数等于此值，若有新的消息，会默认将队列最前面的消息发送到死信交换机
            args.put("x-max-length", 1);
            channel.queueDeclare(queueName, false, false, false, args);

            for (String routingKey: routingKeys) {
                channel.queueBind(queueName, exchangeName, routingKey);
            }

            // 此值设置的是消费者的缓冲的消息数，包括正在处理的消息，并不是队列的中的消息数
            // 若缓冲再消费者的消息并不算作 TTL 的时间，因为并不在队列中
            channel.basicQos(1);

            System.out.println(queueName + " 等待接收消息.........");

            // 创建消费者
            channel.basicConsume(queueName, false, deliverCallback, cancelCallback);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

}


class DLConsume extends Thread{

    private final String DLExchangeName;
    private final String DLQueueName;
    private final String DLRoutingKey;

    public DLConsume(String dlExchangeName, String dlQueueName, String dlRoutingKey){
        this.DLExchangeName = dlExchangeName;
        DLQueueName = dlQueueName;
        DLRoutingKey = dlRoutingKey;
    }

    @Override
    public void run(){

        try {
            Channel channel = MQChannel.getChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());

                System.out.println(DLQueueName + ": " + message);

                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };

            CancelCallback cancelCallback = (consumerTag) -> System.out.println("消息消费被中断");

            // 创建死信交换机，与普通交换机相同
            channel.exchangeDeclare(DLExchangeName, "direct");
            // 创建死信队列
            channel.queueDeclare(DLQueueName, false, false, false, null);
            // 死信队列与死信交换机绑定
            channel.queueBind(DLQueueName, DLExchangeName, DLRoutingKey);

            System.out.println(DLQueueName + " 等待接收消息.........");

            channel.basicConsume(DLQueueName, false, deliverCallback, cancelCallback);

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }

}