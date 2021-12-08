package com.redis.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


import java.rmi.UnknownHostException;



@Configuration
// 若不创建此 RedisConfig 会使用默认的 RedisTemplate
public class RedisConfig {

    @Bean // 产生一个bean的方法，并且将其交给Spring容器管理
    @SuppressWarnings("all") // 选择性地取消特定代码段（即，类或方法）中的警告
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) throws UnknownHostException {

        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(factory);

        /*
         * 进行序列化设置，如果不设置的话，就会使用默认的 jdk的序列化类，
         * 在往 redis保存一个 java对象时，若此对象没有实现 Serializable接口，在保存时就会报错，
         * 即使实现了此接口，在 java中虽然可以保存和获取（获取之后自动反序列化），
         * 但在终端中查看此数据时会发现数据是乱码（序列化之后的对象）
         * 可以通过替换默认的 序列化类来解决这些问题，替换后的 序列化类并不会将对象序列化成乱码，而是 字符串 或 json
         */
        // key、hash的key 采用 String 序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // value、hash的value 采用 Jackson 序列化方式
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();

        return template;
    }
}

