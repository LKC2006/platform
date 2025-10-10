package com.tradeplatform.config;

/*

豆包写的，localdatetime的格式问题

 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Configuration
public class JacksonConfig {

    // 自定义 LocalDateTime 序列化格式（统一为 "yyyy-MM-dd HH:mm:ss"）
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.build();

        // 1. 注册 JavaTimeModule（处理 LocalDateTime 的核心）
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 2. 自定义 LocalDateTime 序列化器（可选，统一格式）
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATETIME_FORMAT));

        // 3. 将模块添加到 ObjectMapper
        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }
}
