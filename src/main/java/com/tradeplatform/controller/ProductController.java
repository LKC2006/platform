package com.tradeplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tradeplatform.pojo.Product;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.service.ProductService;
import com.tradeplatform.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;//controller调用service,service调用dao
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    //一天内发布商品
    @GetMapping("/product/select/24h")
    public List<Product> selectNear() throws SQLException {
        return productService.selectNear();
    }

    //价格区间查询
    @GetMapping("/product/select/price")
    public List<Product> selectPrice(@RequestParam BigDecimal price1,@RequestParam BigDecimal price2) throws SQLException{
        return productService.selectPrice(price1,price2);
    }

    //动态查询（标题，种类，状态等）
    @GetMapping("/product/dynamicselect")
    public List<Product> dynamicSelect(@RequestBody Product product) throws SQLException {
        return productService.dynamicSelect(product);//传入product，传出结果
    }

    //查看全部商品
    @GetMapping("/product/select/all")
    public List<Product> list() throws SQLException {

        return productService.list();
    }

    //根据标题查询商品详情,可以查询多个（返回List）
    @GetMapping("/product/select")
    public List<Product> selectByTitle(@RequestParam String title) throws SQLException {
        try {
            System.out.println("controller" + title);

            return productService.selectByTitle(title);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("controller错误");
            return null;
        }
    }

    //查询商品列表
    @GetMapping("/product/select/title")
    public List<String> getTitle() throws SQLException {
        return productService.getTitle();
    }

    //根据id删除商品信息
    @RequestMapping("/product/delete")
    public ResponseEntity<String> delete(@RequestParam Integer id,
                                         @RequestHeader(value = "token") String token)throws SQLException {//绑定传入路径id到参数id
        Claims claims = JwtUtils.parseToken(token);
        Integer userid = claims.get("id",Integer.class);
        String role = productService.selectRole(userid);
        if(id == null||id <= 0){
            Result error = Result.fail("BadRequest");
            String res = JSONObject.toJSONString(error);
            return ResponseEntity.status(400).body(res);
        }
        Product product = productService.selectById(id);
        if(product == null){
            Result error = Result.fail("Product Doesn't Exist");
            String res = JSONObject.toJSONString(error);
            return ResponseEntity.status(404).body(res);
        }

        if(role.equals("USER")){//不可以用==，会比较地址
            if(productService.selectUserId(id) != userid){//商品卖家id和当前操作id
                Result error = Result.fail("403权限不足");
                //目前仍然不是json且无法自动转换
                String notlogin = JSONObject.toJSONString(error);
                return ResponseEntity.status(403).body(notlogin);
            }
        }
        int i = productService.delete(id);
        Result success = Result.complete(i + "个商品删除成功200");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(200).body(res);
    }

    //新增商品信息
    @RequestMapping("/product/create")
    public ResponseEntity<String> insert(@RequestBody Product product,
                                         @RequestHeader(value = "token") String token) throws SQLException {
        //鉴权
        Claims claims = JwtUtils.parseToken(token);
        Integer id = claims.get("id",Integer.class);
        String role = productService.selectRole(id);
        if(role.equals("USER")){//不可以用==比较地址，放行管理员
            if(product.getPublisher_id() != id){//不能以别人的号发布商品
                Result error = Result.fail("403权限不足");
                String notlogin = JSONObject.toJSONString(error);
                return ResponseEntity.status(403).body(notlogin);
            }
        }

        int i = productService.insert(product);
        Result success = Result.complete(i + "个商品添加成功201");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(201).body(res);
    }

    //更新商品信息
    @RequestMapping("/product/update")
    public ResponseEntity<String> update(@RequestBody Product product,
                                         @RequestHeader(value = "token") String token) throws SQLException {
        //鉴权
        Claims claims = JwtUtils.parseToken(token);
        Integer id = claims.get("id",Integer.class);
        String role = productService.selectRole(id);
        if(role.equals("USER")){//放行管理员
            if(product.getPublisher_id() != id){//不可以篡改别人发布的商品
                Result error = Result.fail("403权限不足");
                String notlogin = JSONObject.toJSONString(error);
                return ResponseEntity.status(403).body(notlogin);
            }
        }

        int i = productService.update(product);
        Result success = Result.complete(i + "个商品修改成功200");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(200).body(res);
    }
}
