package com.tradeplatform.controller;

import com.tradeplatform.mapper.ProductOrderMapper;
import com.tradeplatform.pojo.ProductOrder;
import com.tradeplatform.service.ProductOrderService;
import com.tradeplatform.service.ProductOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@RestController
public class ProductOrderController{

    @Autowired
    ProductOrderService productOrderService;

    @RequestMapping("/cancelorder")
    public String cancelOrder (@RequestParam Integer buyerid, @RequestParam Integer productid) throws SQLException {
        productOrderService.cancelOrder(buyerid,productid);
        productOrderService.rollBackStatus(productid);
        return "Order Canceled Successfully";
    }

    @RequestMapping("/placeorder")
    public String placeOrder (@RequestParam Integer buyerid, @RequestParam Integer productid) throws SQLException {
        productOrderService.placeOrder(buyerid,productid);
        productOrderService.alterStatus(buyerid,productid);
        return "Order Placed Successfully";
    }

    @GetMapping("/productorder/select/all")
    public List<ProductOrder> list() {
        return productOrderService.list();
    }

    @RequestMapping("/productorder/delete")
    public String delete(@RequestParam Integer id) {//绑定传入路径id到参数id
        productOrderService.delete(id);
        return "Product Order Deleted";
    }

    @RequestMapping("/productorder/create")
    public String insert(@RequestBody ProductOrder productOrder) {
        productOrderService.insert(productOrder);
        return "Product Order Created";
    }
}
