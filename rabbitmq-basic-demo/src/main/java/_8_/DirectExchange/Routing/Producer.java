package _8_.DirectExchange.Routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Producer {

    private static final String EXCHANGE_NAME = "directRouting";

    public static void main(String[] args) throws Exception {

        ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        ConfirmCallback cleanOutstandingConfirms = (sequenceNumber, multiple) -> {
            if (multiple) {
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(
                        sequenceNumber, true
                );
                confirmed.clear();
            } else {
                outstandingConfirms.remove(sequenceNumber);
            }
            System.out.println(sequenceNumber + " success!");
        };

        ConfirmCallback nackCallback = (sequenceNumber, multiple) -> {
            String body = outstandingConfirms.get(sequenceNumber);
            System.err.format(
                    "Message with body %s has been nack-ed. Sequence number: %d, multiple: %b%n",
                    body, sequenceNumber, multiple
            );
            cleanOutstandingConfirms.handle(sequenceNumber, multiple);
        };

        Channel channel = MQChannel.getChannel();

        channel.confirmSelect();

        channel.addConfirmListener(cleanOutstandingConfirms, nackCallback);

        // 创建一个 direct 类型的 交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String message;

        String[] routingKeys = {"routingOne", "routingTwo", "routingThree"};

        for (String routingKey: routingKeys) {

            message = routingKey + "'s Message";

            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);

            // 与此前的时间发布使用的方法不同
            // 第一个参数是 交换机名称
            // 第二个参数是 RoutingKey，用于告诉交换机要交给那个队列
            channel.basicPublish(
                    EXCHANGE_NAME, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8)
            );

        }

    }
}
