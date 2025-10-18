package com.tradeplatform.config;

import com.fasterxml.jackson.databind.ObjectMapper; //ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // 时间模块
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
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.time.Duration;

@Configuration
public class RedisConfig {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final String REDIS_PASSWORD = "123456";

    //配置Redis连接工厂（Lettuce RESP2 协议）
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(REDIS_HOST);
        redisConfig.setPort(REDIS_PORT);
        redisConfig.setPassword(REDIS_PASSWORD);

        //Lettuce 客户端配置
        ClientOptions clientOptions = ClientOptions.builder()
                .protocolVersion(ProtocolVersion.RESP2)//RESP2才可以兼容
                .build();

        LettuceClientConfiguration lettuceConfig = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofSeconds(10))
                .build();

        return new LettuceConnectionFactory(redisConfig, lettuceConfig);
    }
    //Java对象在redis中存储和读取，把Java对象转为字节数组存储。重新配置流程模板redistemplate并改变序列化器
    //此处不需要配置RedisTemplate，程序中没有直接操作redis
    //配置RedisCacheManager（@Cacheable处）
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
        //ObjectMapper 配置
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.activateDefaultTyping(
                com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL
        );
        Jackson2JsonRedisSerializer<Object> jacksonSerializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, Object.class); //用对象映射类配置序列器

        //缓存配置，键字符串，值对象，从而区别配置
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(cacheConfig)
                .build();
    }
}
//StringRedisSerializer Jackson2JsonRedisSerializer前者负责把键（都是字符串）序列化为字节数组（反之），而后者负责把值（Java对象，如LocalDateTime，有一个中间过程为转为JSON字符串）序列化。
//手动操作Redis（程序中没有）和运用Spring的缓存注解
//activateDefaultTyping可以避免反序列化是全部都被转为LinkedHashMap