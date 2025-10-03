package com.tradeplatform.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    private static String signingKey = "tradeplatform///1231342142r2ed3pefowpijrfowifuvidsfuhvidfuvhwrlsq";
    private static long expire = 43200000L;//+expire

    public static String generateToken(Map<String, Object> claims){
//        Map<String, Object> claims = new HashMap<String, Object>();
//        claims.put("username", "lkc");

        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, signingKey)
                .addClaims(claims)//自定义内容
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }
    public static Claims parseToken(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }
}
