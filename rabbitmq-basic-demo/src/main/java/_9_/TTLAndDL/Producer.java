package _9_.TTLAndDL;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Producer {

    private static final String EXCHANGE_NAME = "TTLExp";

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

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String message;

        String[] routingKeys = {"routingOne", "routingTwo", "routingThree"};

        for (int i = 0; i < 3; i++) {
            for (String routingKey: routingKeys) {

                message = i + " " + routingKey + "'s Message";

                outstandingConfirms.put(channel.getNextPublishSeqNo(), message);

                // 在每个消息的层次上设置此消息的存活时间
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .expiration("20000")
                        .build();

                channel.basicPublish(
                        EXCHANGE_NAME, routingKey, properties, message.getBytes(StandardCharsets.UTF_8)
                );

                Thread.sleep(50);

            }
        }
    }
}
