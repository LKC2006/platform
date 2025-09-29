package com.tradeplatform.controller;

import com.tradeplatform.mapper.UserMapper;
import com.tradeplatform.pojo.User;
import com.tradeplatform.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    @Autowired
    UserServiceImpl userService;

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

    @RequestMapping("/user/create")
    public String insert(@RequestBody User user) {
        if(userService.insert(user) == 1) {
            return "User Created";
        }
        return "User Not Created";
    }
}
//@PathVariable 是从 URL 路径中获取参数（如 user/delete/7 中的 7），
// 而 @RequestParam 是从 URL 的查询参数（Query String）中获取参数（如 user/delete?id=7 中的 7）。

