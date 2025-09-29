package com.tradeplatform.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public String handleException(IllegalArgumentException e) {
        return e.getMessage();//自动捕获这一种exception,获取后面参数信息
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public String handleDbException(SQLException e) {
        return "Database Error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(RuntimeException e) {
        return e.getMessage();
    }
}