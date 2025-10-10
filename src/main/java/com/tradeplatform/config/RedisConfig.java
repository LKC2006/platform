package com.tradeplatform.config;

/*

豆包写的，用于解决我实在没办法的redis。客户端lettuce协议问题

 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    /**
     * 配置 RedisTemplate：解决 setObjectMapper 弃用问题，用构造函数绑定 ObjectMapper
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // 1. 自定义 ObjectMapper（支持 LocalDateTime、复杂类型）
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持 Java8 时间类型
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁用时间戳
        // 启用类型信息（确保反序列化时识别 List<Product> 等类型）
        objectMapper.activateDefaultTyping(
                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        // 2. 创建 Jackson2JsonRedisSerializer：用双参构造函数传入 ObjectMapper（替代 setObjectMapper）
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class); // 关键修正

        // 3. 配置 key 序列化器（字符串类型）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 4. 绑定序列化器到 RedisTemplate
        redisTemplate.setKeySerializer(stringSerializer); // key 用字符串序列化
        redisTemplate.setValueSerializer(jacksonSerializer); // value 用 Jackson 序列化
        redisTemplate.setHashKeySerializer(stringSerializer); // hash key 序列化
        redisTemplate.setHashValueSerializer(jacksonSerializer); // hash value 序列化

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * 配置 RedisCacheManager：确保 @Cacheable 注解也用修正后的 Jackson 序列化
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        // 1. 同样用双参构造函数创建序列化器
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.activateDefaultTyping(
                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class); // 关键修正

        // 2. 构建缓存配置（指定序列化器和过期时间）
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)) // 缓存默认1小时过期（可选）
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer))
                .disableCachingNullValues(); // 不缓存null值

        // 3. 创建 CacheManager
        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}