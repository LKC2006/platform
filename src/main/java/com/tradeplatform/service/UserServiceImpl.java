package com.tradeplatform.service;

import com.tradeplatform.mapper.UserMapper;
import com.tradeplatform.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);

    }

    @Override
    public int insert(User user) {
         if(user.getPhone().length()!=11){
             return 0;
         }
         return 1;
    }

    @Override
    public void update(User user) {
        userMapper.update(user);

    }
}
