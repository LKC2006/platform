package com.tradeplatform.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.tradeplatform.pojo.Result;
import com.tradeplatform.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    //先过滤后拦截
    @Override//前运行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求url
        String url = request.getRequestURL().toString();
        log.info("url={}", url);

        //获取请求头中的令牌
        String jwt = request.getHeader("token");

        //判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("user did not login");
            Result error = Result.fail("401未授权");
            //目前仍然不是json且无法自动转换
            String notlogin = JSONObject.toJSONString(error);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(notlogin);
            response.setStatus(401);
            return false;
        }

        //校验令牌
        try{
            JwtUtils.parseToken(jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("Invalid Token");
            Result error = Result.fail("401未授权");
            //目前仍然不是json且无法自动转换
            String notlogin = JSONObject.toJSONString(error);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(notlogin);
            response.setStatus(401);
            //response.getWriter().write("401未授权");//这个可以直接返回401未授权而不是result类型
            return false;
        }

        //放行
        log.info("Valid Token") ;
        return true;//放行
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
