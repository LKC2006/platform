package com.tradeplatform.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)//只向前端返回非空项
public class Result {

    String username;
    String password;

    int code = 0;
    String msg = "";
    String data = null;

    public static Result success(String token){
        Result result = new Result();
        result.code = 1;
        result.msg = "success";
        result.data = token;
        return result;
    }
    public static Result fail(String message){
        Result result = new Result();
        result.code = 0;
//        result.msg = "fail";
        result.msg = message;
        return result;
    }
}
