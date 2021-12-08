package _6_.PublisherConfirm.AsynchronousConfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import utils.MQChannel;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class Producer {
    private final static String QUEUE_NAME = "publisher confirm";

    public static void main(String[] args) throws Exception {

        // 用于暂时的存储消息，从而可以找到出错的消息
        // 此数据结构是 跳表，按照 key 的大小排列，查找速度快
        ConcurrentNavigableMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        // 确认收到消息的一个回调
        // 参数：
        // 1.消息序列号
        // 2.是否批量确认
        ConfirmCallback cleanOutstandingConfirms = (sequenceNumber, multiple) -> {
            if (multiple) {
                // headMap方法 返回此映射的 部分视图（还是原来的数据，并没有对部分原有数据进行复制），
                // 其键小于（或等于，如果 inclusive 为 true）toKey。
                // 对返回的映射的数据的修改会同步到原有的映射上
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(
                        sequenceNumber, true
                );
                // 从此映射中移除所有数据。
                // 因为此映射是 另一个映射的视图，所以另一个映射中的此部分数据就会被删除
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
            // 删除错误数据
            cleanOutstandingConfirms.handle(sequenceNumber, multiple);
        };

        Channel channel = MQChannel.getChannel();

        // 开启发布确认
        channel.confirmSelect();

        // 添加一个异步确认的监听器
        // 参数：
        // 1.确认收到消息的回调
        // 2.未收到消息的回调
        channel.addConfirmListener(cleanOutstandingConfirms, nackCallback);

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        String message;
        long start = System.currentTimeMillis();

        for (int i = 1; i <= 1000; i++) {

            message = "Message " + i;

            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);

            channel.basicPublish(
                    "", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8)
            );

        }

        System.out.println("Cost " + (System.currentTimeMillis() - start) + " ms");

    }
}
