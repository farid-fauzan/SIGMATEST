package com.sigma.rest.utility;

import com.sigma.rest.entity.User;
import com.sigma.rest.pojo.UserPojo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("idUser", user.getIdUser());
        claims.put("email", user.getEmail());
        claims.put("nama", user.getNama());
        claims.put("telepon", user.getTelepon());

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        TokenManager.addToken(token);

        return token;


    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public UserPojo getDataToken(String token) {
        UserPojo userPojo = new UserPojo();
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        userPojo.setEmail((String) claims.get("email"));
        return userPojo;
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = extractExpirationDate(token);
        return expirationDate.before(new Date());
    }

    public void invalidateToken(String token) {
        // Hapus token dari daftar yang berlaku saat logout
        TokenManager.removeToken(token);
    }




}
