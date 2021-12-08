package com.redis.demo;

import com.redis.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class RedisPracticeApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        // redisTemplate 操作不同的数据类型，api和指令是一样的
        // opsForValue 操作字符串 类似String
        // opsForList 操作 List 类似List
        // opsForHash 操作 Hash

        // 除了基本的操作，我们常用的方法都可以直接通过redisTemplate操作，比如事务和基本的CRUD

        // 获取连接对象
        // RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        // connection.flushDb();
        // connection.flushAll();

        User user = new User();
        user.setName("test");
        redisTemplate.opsForValue().set("key", user);
        System.out.println(redisTemplate.opsForValue().get("key"));
        redisTemplate.delete("key");

    }

}
