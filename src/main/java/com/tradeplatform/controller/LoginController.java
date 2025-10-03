package com.tradeplatform.controller;

import com.tradeplatform.pojo.Result;
import com.tradeplatform.pojo.User;
import com.tradeplatform.service.UserService;
import com.tradeplatform.service.UserServiceImpl;
import com.tradeplatform.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.tradeplatform.pojo.Result.fail;
import static com.tradeplatform.pojo.Result.success;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping ("/user/login")
    public Result login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        if(username== null || password== null){
            throw new RuntimeException( "Both Username and Password Cannot be null");
        }
        if(userService.login(username,password) != null){
            User userfull = userService.login(username,password);
            log.info("User {} Login Success",username);//在日志中显示登录
            Map<String,Object> claims = new HashMap<>();
            claims.put("username",username);
            claims.put("id",userfull.getId());
            String jwt = JwtUtils.generateToken(claims);
            return success(jwt);
        }
        else {
            return fail("Wrong Username/Password");
        }
    }
}
