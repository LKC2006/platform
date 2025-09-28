package com.tradeplatform.controller;

import com.tradeplatform.mapper.UserMapper;
import com.tradeplatform.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/user/select/all")
//    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @RequestMapping("/user/delete")
    public String delete(@RequestParam Integer id) {//绑定传入路径id到参数id
        userMapper.delete(id);
        return "User Deleted";
    }

    @RequestMapping("/user/create")
    public String insert(@RequestBody User user) {
        userMapper.insert(user);
        return "User Created";
    }
}
//@PathVariable 是从 URL 路径中获取参数（如 user/delete/7 中的 7），
// 而 @RequestParam 是从 URL 的查询参数（Query String）中获取参数（如 user/delete?id=7 中的 7）。

