package com.tradeplatform.filter;

import com.alibaba.fastjson.JSONObject;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
//@WebFilter(urlPatterns = "/*")
@WebFilter(urlPatterns = "/filter")
public class AFilter implements jakarta.servlet.Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //一般传递给此方法的对象本身就是HttpServletRequest,因此可以安全强转
        HttpServletRequest req = (HttpServletRequest) request;//强转
        HttpServletResponse res = (HttpServletResponse) response;

        //获取请求url
        String url = req.getRequestURL().toString();
        log.info("url={}", url);

        //判断请求的url是否包含login
        if(url.contains("login")){
            log.info("login");
            chain.doFilter(request, response);//放行
            return;
        }

        //获取请求头中的令牌，Http特有信息
        String jwt = req.getHeader("token");

        //判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("user did not login");
            Result error = Result.fail("User Did Not Login");
            //目前仍然不是json且无法自动转换
            String notlogin = JSONObject.toJSONString(error);//String类型的变量，但是内容符合JSON格式
            res.getWriter()/*获取字符流输出对象*/.write(notlogin);//字符串写入响应体
            return;
        }

        //校验令牌
        try{
            JwtUtils.parseToken(jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("Invalid Token");
            Result error = Result.fail("User Did Not Login");
            //目前仍然不是json且无法自动转换
            String notlogin = JSONObject.toJSONString(error);
            res.getWriter().write(notlogin);
            return;


        }

        //放行

        log.info("Valid Token") ;
        chain.doFilter(request,response);//放行


    }
}
