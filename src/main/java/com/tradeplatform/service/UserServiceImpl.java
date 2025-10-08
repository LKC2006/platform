package com.tradeplatform.service;

import com.tradeplatform.mapper.UserMapper;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.pojo.User;
//import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
//@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void toAdmin(Integer id) {
        userMapper.toAdmin(id);
    }

    //登录
    @Override
    public User login(String username, String password){

        try {
            User user = userMapper.getUserByUsername(username);
            boolean isMatch = BCrypt.checkpw(password,user.getPassword());
            if(!isMatch){
                throw new RuntimeException("Wrong password");
            }
            return userMapper.login(username);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    //查询全部
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    //删除
    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    //注册
    @Override
    public int register(User user) throws SQLException {
         if(user.getPhone().length()!=11){//电话号码只能为11位
             throw new RuntimeException("Phone Numbers Must Contain 11 Digits");
         }

         String rawPassword = user.getPassword();
         String encodedPassword = BCrypt.hashpw(rawPassword,BCrypt.gensalt());
         user.setPassword(encodedPassword);
         try {
             userMapper.register(user);
         } catch (Exception e) {
             throw new RuntimeException("Register Failed");
         }
         return 1;
    }
}
