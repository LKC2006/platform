package com.tradeplatform.service;

import com.tradeplatform.mapper.UserMapper;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void toAdmin(Integer id) {
        userMapper.toAdmin(id);
    }

    @Override
    public User login(String username, String password){

        try {
            User user = userMapper.getUserByUsername(username);
            boolean isMatch = passwordEncoder.matches(password,user.getPassword());
            if(!isMatch){
                throw new RuntimeException("Wrong password");
            }
            return userMapper.login(username,password);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);

    }

    @Override
    public int register(User user) {
         if(user.getPhone().length()!=11){//电话号码只能为11位
             throw new RuntimeException("Phone Numbers Must Contain 11 Digits");
         }

         String rawPassword = user.getPassword();
         String encodedPassword = passwordEncoder.encode(rawPassword);
         user.setPassword(encodedPassword);
         userMapper.register(user);
         return 1;
    }

//    @Override
//    public void update(User user) {
//        userMapper.update(user);
//    }
}
