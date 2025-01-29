package com.klccgr.SchoolManagementSystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${sms.app.secret}")
    private String APP_SECRET;
    @Value("${sms.app.expires.in}")
    private Long EXPIRES_IN;

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal();
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.ES512, APP_SECRET).compact();
    }

    private Jws<Claims> getParsedJwt(String token) {
        return Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
    }

    public Long getUserIdFromJwt(String token) {
        Claims claims = getParsedJwt(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateJwt(String authToken) {
        try{
            getParsedJwt(authToken);
            return !isTokenExpired(authToken);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = getParsedJwt(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
