package _7_.FanoutExchange.PublishOrSubscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Producer {

    private static final String EXCHANGE_NAME = "PublishOrSubscribe";

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

        // 创建一个 fanout类型的 交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        String message;
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 10; i++) {

            message = "Message " + i;

            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);

            // 与此前的时间发布使用的方法不同
            // 第一个参数是 交换机名称
            // 第二个参数是 RoutingKey，用于告诉交换机要交给那个队列，但是 fanout 类型的交换机会忽略掉此参数
            channel.basicPublish(
                    EXCHANGE_NAME, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8)
            );

        }

        System.out.println("Cost " + (System.currentTimeMillis() - start) + " ms");

    }
}
