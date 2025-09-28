package com.tradeplatform;

import com.tradeplatform.mapper.ProductMapper;
import com.tradeplatform.mapper.ProductOrderMapper;
import com.tradeplatform.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@SpringBootApplication
@MapperScan("com.tradeplatform.mapper")
public class MybatisStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(MybatisStartApplication.class, args);}
}

