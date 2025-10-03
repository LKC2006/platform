package com.tradeplatform.controller;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
/*
*
* 这一个类不是我写的
* 是去咸鱼找人解决问题的时候
* 那个人写的
*
 */
@Controller
public class TestController {

    @Resource
    private DataSource dataSource;

    @GetMapping("/db")
    public String getDataSource(){
        try(Connection conn = dataSource.getConnection()) {
            if(conn !=null && !conn.isClosed()) {
                return "1chenggong";
            }

        }catch (SQLException e){
            return "2";
        }
        return "3";
    }
}
