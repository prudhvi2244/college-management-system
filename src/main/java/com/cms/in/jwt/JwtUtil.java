package com.cms.in.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${app.secret_key")
    private String secretKey;


    public Boolean validateToken(String token,String username)
    {
      String tokenUsername= getUsernameFromToken(token);
      return (username.equals(tokenUsername) && !isTokenExpired(token));
    }

    public Boolean isTokenExpired(String token)
    {
        Date TokenExpirationDate=getTokenExpirationDate(token);
        return TokenExpirationDate.before(new Date(System.currentTimeMillis()));
    }

    public Date getTokenExpirationDate(String token)
    {
        return getClaims(token).getExpiration();
    }

    public String getUsernameFromToken(String token)
    {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token)
    {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String username)
    {
        return Jwts.builder()
                    .setId("PRUDHVI2222")
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setIssuer("Prudhvi Raj")
                    .setExpiration(new Date(System.currentTimeMillis()+ TimeUnit.MINUTES.toMillis(5)))
                    .signWith(SignatureAlgorithm.HS512,secretKey.getBytes())
                    .compact();
    }

}
