package com.studentapp.studentApp.util;

import com.studentapp.studentApp.model.Student;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken {
    private final String ISSUER = "WLY";
    @Value("${authentication.jwt.signing-key}")
    private String signingKeyEncoded;

    public Map<String, String> getAccessToken(Student student) {
        Date issuedAt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issuedAt);
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        Date expiresAt = calendar.getTime();
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKeyEncoded));

        String token = Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(String.valueOf(student.getId()))
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .claim("id", student.getId())
                .claim("username", student.getUsername())
                .claim("points", student.getPoints())
                .signWith(key)
                .compact();

        Map<String, String> payload = new HashMap<>();
        payload.put("access_token", token);

        return payload;
    }

    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException exception) {
            return false;
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getBody().get("username").toString();
    }

    public String getId(String token) {
        return getClaims(token).getBody().get("id").toString();
    }

    private Jws<Claims> getClaims(String token) throws JwtException {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(signingKeyEncoded));

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

}

