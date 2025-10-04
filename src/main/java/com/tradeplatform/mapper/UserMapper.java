package com.tradeplatform.mapper;


import com.tradeplatform.pojo.User;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username} and password = #{password}")
    public User login(String username,String password);

    @Select("select * from user")
    public List<User> list();


    @Delete("delete from user where id = #{id}")//利用参数占位符#{}
    public void delete(Integer id);

    @Insert("insert into user(username, role, phone, create_time, update_time) " +
            "VALUES (#{username}, 'USER', #{phone}, #{create_time},#{update_time}/*now(), now()*/)")
    public int insert(User user);

    @Update("update user set username = #{username}, password = #{password}, role = 'USER'," +
            " phone = #{phone}, update_time = #{update_time} where id = #{id}")
    public void update(User user);
}
