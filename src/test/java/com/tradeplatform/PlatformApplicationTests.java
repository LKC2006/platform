package com.tradeplatform;

import com.tradeplatform.mapper.ProductMapper;
import com.tradeplatform.mapper.ProductOrderMapper;
import com.tradeplatform.mapper.UserMapper;
import com.tradeplatform.pojo.Product;
import com.tradeplatform.pojo.ProductOrder;
import com.tradeplatform.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlatformApplicationTests {
    @Autowired
    private ProductMapper productMapper;// 注入接口类型的对象
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private UserMapper userMapper;

}
