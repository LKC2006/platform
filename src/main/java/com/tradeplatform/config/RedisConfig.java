//package com.tradeplatform.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import io.lettuce.core.ClientOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import io.lettuce.core.protocol.ProtocolVersion;
//
//
//import java.time.Duration;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class RedisConfig {
//
//    // 2. 核心：配置 Lettuce 客户端使用 RESP2 协议（关键新增）
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//
//        // 1. 配置 Redis 服务器信息
//        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
//        redisConfig.setHostName("127.0.0.1");
//        redisConfig.setPort(6379);
//        redisConfig.setPassword("123456");
//
//            // 2. 配置 Lettuce 客户端选项（核心：在这里设置协议版本）
//        ClientOptions clientOptions = ClientOptions.builder()
//                .protocolVersion(ProtocolVersion.RESP2) // 这行才是正确的！
//                .build();
//
//            // 3. 构建 Lettuce 客户端配置，关联上面的 ClientOptions
//        LettuceClientConfiguration lettuceConfig = LettuceClientConfiguration.builder()
//                .clientOptions(clientOptions) // 仅关联，不直接调用 protocolVersion
//                .build();
//
//            // 4. 创建连接工厂
//        return new LettuceConnectionFactory(redisConfig, lettuceConfig);
//
//    }
//
//    // 3. 配置 RedisTemplate（修复 Jackson 序列化器构造函数顺序）
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        // 关联上面配置的 RESP2 连接工厂（必须！否则用默认连接工厂）
//        redisTemplate.setConnectionFactory(connectionFactory);
//
//        // 3.1 自定义 ObjectMapper（支持 LocalDateTime + 复杂类型）
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule()); // 解决 LocalDateTime 序列化
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁用时间戳
//        objectMapper.activateDefaultTyping(
//                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL // 支持泛型（如 List<Product>）
//        );
//
//        // 3.2 修复 Jackson 序列化器构造函数顺序（正确顺序：Class<T> 在前，ObjectMapper 在后）
//        // 之前你写反了（objectMapper在前），导致构造函数无法解析，这里纠正
//        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
//                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);;
//
//        // 3.3 配置 Key/Value 序列化器
//        StringRedisSerializer stringSerializer = new StringRedisSerializer(); // Key 用字符串
//        redisTemplate.setKeySerializer(stringSerializer);
//        redisTemplate.setValueSerializer(jacksonSerializer);
//        redisTemplate.setHashKeySerializer(stringSerializer);
//        redisTemplate.setHashValueSerializer(jacksonSerializer);
//
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//
//
//    // 4. 配置 RedisCacheManager（@Cacheable 注解专用）
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
//        // 4.1 复用 Jackson 配置
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.activateDefaultTyping(
//                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
//                ObjectMapper.DefaultTyping.NON_FINAL
//        );
//        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
//                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);; // 同样纠正顺序
//
//        // 4.2 构建缓存配置（序列化器 + 过期时间）
//        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(1)) // 缓存默认 1 小时过期
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer))
//                .disableCachingNullValues(); // 不缓存 null（避免缓存穿透）
//
//        // 4.3 创建 CacheManager（关联 RESP2 连接工厂）
//        return RedisCacheManager.builder(connectionFactory)
//                .cacheDefaults(cacheConfig)
//                .build();
//    }
//}

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
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class); // 这里绝对不能写反！

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