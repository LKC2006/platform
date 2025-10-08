package com.tradeplatform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //需要设置复杂密码
    private static String signingKey = "tradeplatform///1231342142r2ed3pefowpijrfowifuvidsfuhvidfuvhwrlsq";
    private static long expire = 43200000L;//+expire 12h

    public static String generateToken(Map<String, Object> claims){

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .addClaims(claims)//加入外部自定义内容
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();//最终组装令牌
        return jwt;
    }

    public static Claims parseToken(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwt)
                .getBody();//提取Payloads(中间包含claims)
        return claims;
    }
}
