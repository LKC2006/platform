package com.tradeplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tradeplatform.pojo.ProductOrder;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.service.ProductOrderService;
import com.tradeplatform.service.ProductService;
import com.tradeplatform.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
@RestController
public class ProductOrderController{

    @Autowired
    ProductOrderService productOrderService;

    @Autowired
    ProductService productService;//为了通过商品id查出发布者id

    @RequestMapping("/cancelorder")
    public ResponseEntity<String> cancelOrder (@RequestParam Integer buyerid, @RequestParam Integer productid,
                                               @RequestHeader(value = "token") String token) throws SQLException {
        //鉴权
        Claims claims = JwtUtils.parseToken(token);
        Integer id = claims.get("id",Integer.class);
        String role = productService.selectRole(id);
        if(role.equals("USER")){//放行管理员
            if(productService.selectUserId(productid) != id && buyerid != id ){//操作者不是买家/卖家
                Result error = Result.fail("403权限不足");
                String notaccessible = JSONObject.toJSONString(error);
                return ResponseEntity.status(403)
                        .body(notaccessible);
            }
        }

        productOrderService.cancelOrder(buyerid,productid);
        productOrderService.rollBackStatus(productid);

        Result success = Result.complete("Order Cancelled 200");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(200)
                .body(res);
    }

    @RequestMapping("/placeorder")
    public ResponseEntity<String> placeOrder (@RequestParam Integer buyerid, @RequestParam Integer productid,
                                              @RequestHeader(value = "token") String token) throws SQLException {


        Claims claims = JwtUtils.parseToken(token);
        Integer id = claims.get("id",Integer.class);
        String role = productService.selectRole(id);
        if(role.equals("USER")){//放行管理员
            if(productService.selectUserId(productid) == buyerid){//不能买自己的东西
                Result error = Result.fail("403权限不足，不能买自己发布的商品");
                String notaccessible = JSONObject.toJSONString(error);
                return ResponseEntity.status(403)
                        .body(notaccessible);
            }
        }

        productOrderService.placeOrder(buyerid,productid);
        productOrderService.alterStatus(buyerid,productid);

        Result success = Result.complete("Order Placed Successfully 200");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(200)
                .body(res);
    }

    @GetMapping("/productorder/select/all")//查询所有订单
    public List<ProductOrder> list() {
        return productOrderService.list();
    }
}
