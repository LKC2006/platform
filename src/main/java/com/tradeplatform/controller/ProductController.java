package com.tradeplatform.controller;

import com.tradeplatform.pojo.Product;
import com.tradeplatform.service.ProductService;
import com.tradeplatform.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;//controller调用service,service调用dao
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/product/select/24h")
    public List<Product> selectNear() throws SQLException {
        return productService.selectNear();
    }

    @GetMapping("/product/select/price")
    public List<Product> selectPrice(@RequestParam BigDecimal price1,@RequestParam BigDecimal price2) throws SQLException{
        return productService.selectPrice(price1,price2);
    }

    @GetMapping("/product/dynamicselect")
    public List<Product> dynamicSelect(@RequestBody Product product) throws SQLException {
        return productService.dynamicSelect(product);//传入product，传出结果
    }

    @GetMapping("/product/select/all")
    //@Override
    public List<Product> list() throws SQLException {

        return productService.list();
    }

    //根据标题查询商品详情,可以查询多个
    @GetMapping("/product/select")
    public List<Product> selectByTitle(@RequestParam String title) throws SQLException {
        return productService.selectByTitle(title);
    }



    //查询商品列表
    @GetMapping("/product/select/title")
    public List<String> getTitle() throws SQLException {
        return productService.getTitle();
    }

    //根据id删除商品信息
    @RequestMapping("/product/delete")
    public String delete(@RequestParam Integer id) throws SQLException {//绑定传入路径id到参数id
        if(id == null||id <= 0){
            return "Invalid id";
        }
        Product product = productService.selectById(id);
        if(product == null){
            return "Product Doesn't Exist";
        }
        int i = productService.delete(id);
        if (i > 0) {
            return i + " " + "Product(s) Deleted";
        }
        return "Products Not Deleted";//i=0表示没有对数据库进行操作，返回提示
    }

    //新增商品信息
    @RequestMapping("/product/create")
    public String insert(@RequestBody Product product) throws SQLException {

        int i = productService.insert(product);
        if (i > 0) {
            return i + " " + "Product Created";
        }
        return "Product Not Created";//i=0表示没有对数据库进行操作，返回提示
    }

    //更新商品信息
    @RequestMapping("/product/update")
    public String update(@RequestBody Product product) throws SQLException {
        int i = productService.update(product);
        if (i > 0) {
            return i + " " + "Product Updated";
        }
        return "Product Not Updated";//i=0表示没有对数据库进行操作，返回提示
    }
}
