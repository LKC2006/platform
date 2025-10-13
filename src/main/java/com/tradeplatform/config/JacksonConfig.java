package com.tradeplatform.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class JacksonConfig {//Spring官方整合的JSON处理组件，序列化与反序列化，用于前端和controller之间的JSON数据交互

    // 配置 HTTP 响应的 JSON 序列化（解决 LocalDateTime 响应报错）
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {//用于把controller返回的数据序列化为JSON字符串，同时把前端发送的JSON反序列化为Java对象
        // 正确创建 ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持 LocalDateTime
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // 禁用时间戳
        // 统一时间格式（可选，避免默认带 T 的格式）
        objectMapper.setDateFormat(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 创建消息转换器
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }
}