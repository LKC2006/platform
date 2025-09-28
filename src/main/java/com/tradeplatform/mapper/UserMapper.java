package com.tradeplatform.mapper;


import com.tradeplatform.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    public List<User> list();


    @Delete("delete from user where id = #{id}")//利用参数占位符#{}
    public void delete(Integer id);

    @Insert("insert into user(username, role, phone, create_time, update_time) " +
            "VALUES (#{username}, #{role}, #{phone}, #{create_time},#{update_time}/*now(), now()*/)")
    public void insert(User user);

    @Update("update user set username = #{username}, password = #{password}, role = #{role}," +
            " phone = #{phone}, update_time = #{update_time} where id = #{id}")
    public void update(User user);
}
