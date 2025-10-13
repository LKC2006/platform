package com.tradeplatform.config;

import com.fasterxml.jackson.databind.ObjectMapper; // 必须导入 ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // 必须导入时间模块
import com.fasterxml.jackson.databind.SerializationFeature;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.protocol.ProtocolVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    // 硬编码 Redis 配置（按你服务器的实际配置改！）
    private static final String REDIS_HOST = "127.0.0.1"; // 服务器Redis地址
    private static final int REDIS_PORT = 6379; // 服务器Redis端口
    private static final String REDIS_PASSWORD = "123456"; // 服务器Redis密码（无则改为""）


    // 1. 配置 Redis 连接工厂（Lettuce + RESP2 协议）
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // Redis 服务器配置
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(REDIS_HOST);
        redisConfig.setPort(REDIS_PORT);
        redisConfig.setPassword(REDIS_PASSWORD);


        // Lettuce 客户端配置（RESP2 协议）
        ClientOptions clientOptions = ClientOptions.builder()
                .protocolVersion(ProtocolVersion.RESP2)
                .build();

        LettuceClientConfiguration lettuceConfig = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(10))
                .build();

        return new LettuceConnectionFactory(redisConfig, lettuceConfig);
    }

//Java对象在redis中存储和读取，把Java对象转为字节数组存储。重新配置流程模板redistemplate并改变序列化器
    // 2. 配置 RedisTemplate（关键：正确创建 ObjectMapper 和序列化器）
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // -------------- 关键：正确创建 ObjectMapper --------------
        ObjectMapper objectMapper = new ObjectMapper();
        // 注册 Java8 时间模块（解决 LocalDateTime 序列化）
        objectMapper.registerModule(new JavaTimeModule());
        // 禁用时间戳（避免转成数字）
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 支持泛型（如 List<Product>）
        objectMapper.activateDefaultTyping(
                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        // -------------- 关键：正确创建 Jackson 序列化器（参数顺序：Class在前，ObjectMapper在后）--------------
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);

        // 配置 Key 序列化器（字符串）
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 绑定序列化器
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jacksonSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jacksonSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    // 3. 配置 RedisCacheManager（@Cacheable 专用）
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        // 复用 ObjectMapper 配置
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.activateDefaultTyping(
                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class); // 同样不能写反

        // 缓存配置
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
//StringRedisSerializer Jackson2JsonRedisSerializer前者负责把键（都是字符串）序列化为字节数组（反之），而后者负责把值（Java对象，如LocalDateTime，有一个中间过程为转为JSON字符串）序列化。
//手动操作Redis（程序中没有）和运用Spring的缓存注解
//activateDefaultTyping可以避免反序列化是全部都被转为LinkedHashMap