package com.tradeplatform.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//只向前端返回非空项
public class Result {

    String username;
    String password;

    int code = 0;
    String msg = "";
    String data = null;

    public Result() {

    }

    public static Result success(String token) {
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        result.data = token;
        return result;
    }

    //下面两个相互对应
    public static Result complete(String message) {
        Result result = new Result();
        result.code = 1;
        result.msg = message;
        return result;
    }

    public static Result fail(String message) {
        Result result = new Result();
        result.code = 0;
        result.msg = message;
        return result;
    }


    public Result(String username, String password, int code, String msg, String data) {
        this.username = username;
        this.password = password;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
