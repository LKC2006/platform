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

import java.util.List;

@SpringBootTest
class MybatisStartApplicationTests {
    @Autowired
    private ProductMapper productMapper;// 注入接口类型的对象
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testListProduct(){
        List<Product> productList = productMapper.list();
        productList.stream().forEach(product -> {
            System.out.println(product);});//隐式调用to String()
    }
    @Test
    public void testListProductOrder(){
        List<ProductOrder> productOrderList = productOrderMapper.list();
        productOrderList.stream().forEach(productorder -> {
            System.out.println(productorder);});
    }
    @Test
    public void testListUser(){
        List<User> userList = userMapper.list();
        userList.stream().forEach(user -> {
            System.out.println(user);});
    }
    @Test
    public void testDeleteUser(){
        //int delete =
        userMapper.delete(3);
        //System.out.println(delete);
    }

//    void contextLoads() {
//    }

}
