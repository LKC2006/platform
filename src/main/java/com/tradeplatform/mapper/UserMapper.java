package com.tradeplatform.mapper;


import com.tradeplatform.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    public User getUserByUsername(String username);

    @Update("update user set  role = 'ADMIN' where id = #{id}")
    public void toAdmin(Integer id);

    @Select("select * from user where username = #{username}")
    public User login(String username);

    @Select("select * from user")
    public List<User> list();

    @Delete("delete from user where id = #{id}")//利用参数占位符#{}
    public void delete(Integer id);

    //默认USER,创建新用户
    @Insert("insert into user(username,password, role, phone, create_time, update_time) " +
            "VALUES (#{username}, #{password}, 'USER', #{phone}, now(), now())")
    public void register(User user);

}
