package com.tradeplatform.controller;

import com.tradeplatform.mapper.ProductOrderMapper;
import com.tradeplatform.pojo.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProductOrderController{

    @Autowired
    ProductOrderMapper productOrderMapper;

    @GetMapping("/productorder/select/all")
//    @Override
    public List<ProductOrder> list() {
        return productOrderMapper.list();
    }

    @RequestMapping("/productorder/delete")
    public String delete(@RequestParam Integer id) {//绑定传入路径id到参数id
        productOrderMapper.delete(id);
        return "Product Order Deleted";
    }

    @RequestMapping("/productorder/create")
    public String insert(@RequestBody ProductOrder productOrder) {
        productOrderMapper.insert(productOrder);
        return "Product Order Created";
    }
}
