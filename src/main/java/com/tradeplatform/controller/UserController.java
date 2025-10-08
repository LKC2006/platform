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

    //查询所有用户
    @GetMapping("/user/select/all")
    public List<User> list() {
        return userService.list();
    }

    //删掉某个用户
    @RequestMapping("/user/delete")
    public String delete(@RequestParam Integer id) {//绑定传入路径id到参数id
        userService.delete(id);
        return "User Deleted";
    }

    //注册接口
    @RequestMapping("/user/register")
    public String register(@RequestBody User user) throws SQLException {
        if(userService.register(user) == 1) {
            return "User Registered Successfully";
        }
        return "Failed To Register, Something Went Wrong";
    }

    //管理员升级USER为ADMIN
    @RequestMapping("/user/toadmin")
    public ResponseEntity<String> toAdmin(@RequestParam Integer id,
                                          @RequestHeader(value = "token") String token) throws SQLException {
        //鉴权
        Claims claims = JwtUtils.parseToken(token);//一个JSON对象，保存身份信息，Claims类更适合JWT
        Integer operatorid = claims.get("id",Integer.class);
        String role = productService.selectRole(operatorid);
        if(role.equals("USER")){//放行管理员
            Result error = Result.fail("403权限不足");
            String notaccessible = JSONObject.toJSONString(error);
            return ResponseEntity.status(403).body(notaccessible);
        }

        userService.toAdmin(id);

        Result success = Result.complete("Role Altered 200");
        String res = JSONObject.toJSONString(success);
        return ResponseEntity.status(200).body(res);
    }
}


