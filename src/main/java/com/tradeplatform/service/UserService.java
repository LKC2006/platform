package com.tradeplatform.service;

import com.tradeplatform.pojo.Result;
import com.tradeplatform.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {


    public User login(String username, String password);
    public List<User> list();
    public void delete(Integer id);
    public int insert(User user);
    public void update(User user);


}
