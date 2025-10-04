package com.tradeplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.pojo.User;
import com.tradeplatform.service.ProductService;
import com.tradeplatform.service.UserService;
import com.tradeplatform.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;



    @GetMapping("/user/select/all")
//    @Override
    public List<User> list() {
        return userService.list();
    }

    @RequestMapping("/user/delete")
    public String delete(@RequestParam Integer id) {//绑定传入路径id到参数id
        userService.delete(id);
        return "User Deleted";
    }

    @RequestMapping("/user/register")//注册接口
    public String register(@RequestBody User user) throws SQLException {
        if(userService.register(user) == 1) {
            return "User Registered Successfully";
        }
        return "Failed to Register, Something went wrong";
    }

    @RequestMapping("/user/toadmin")
    public ResponseEntity<String> toAdmin(@RequestParam Integer id,
                                          @RequestHeader(value = "token") String token) throws SQLException {
        //鉴权
        Claims claims = JwtUtils.parseToken(token);
        Integer operatorid = claims.get("id",Integer.class);
        String role = productService.selectRole(operatorid);
        if(role.equals("USER")){//放行管理员
            Result error = Result.fail("403权限不足");
            String notaccessible = JSONObject.toJSONString(error);
            return ResponseEntity.status(403)
                        .body(notaccessible);
        }

        userService.toAdmin(id);

        Result success = Result.complete("Role Altered 200");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(200).body(res);
    }
}


