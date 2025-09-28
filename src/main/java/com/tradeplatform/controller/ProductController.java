package com.tradeplatform.controller;

import com.tradeplatform.mapper.ProductMapper;
import com.tradeplatform.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProductController {

    @Autowired
    ProductMapper productMapper;

    @GetMapping("/product/select/all")
    //@Override
    public List<Product> list() {
        return productMapper.list();
    }

    //根据标题查询商品详情,可以查询多个
    @GetMapping("/product/select")
    public List<Product> selectByTitle(@RequestParam String title) {
        return productMapper.selectByTitle(title);
    }

    //查询商品列表
    @GetMapping("/product/select/title")
    public List<String> getTitle() {
        return productMapper.getTitle();
    }

    //根据id删除商品信息
    @RequestMapping("/product/delete")
    public String delete(@RequestParam Integer id) {//绑定传入路径id到参数id
        productMapper.delete(id);
        return "Product Deleted";
    }

    //新增商品信息
    @RequestMapping("/product/create")
    public String insert(@RequestBody Product product) {
        productMapper.insert(product);
        return "Product Created";
    }

    //更新商品信息
    @RequestMapping("/product/update")
    public String update(@RequestBody Product product) {
        productMapper.update(product);
        return "Product Updated";
    }
}
