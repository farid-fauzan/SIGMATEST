package com.sigma.rest.services;

import com.sigma.rest.entity.User;
import com.sigma.rest.utility.MessageModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServices {

    @Value("${jwt.secret}")
    private String secretKey;

    public ResponseEntity extractToken(String token) {
        Map<String, Object> result = new HashMap<>();
        MessageModel msg = new MessageModel();
        try{

            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            User user = new User();
            String email = claims.getSubject();
            Long idUser = claims.get("idUser", Long.class);
            String nama = claims.get("nama", String.class);
            String telepon = claims.get("telepon", String.class);

            user.setIdUser(idUser);
            user.setEmail(email);
            user.setNama(nama);
            user.setTelepon(telepon);

            if(idUser==null || idUser.equals(null)){
                msg.setStatus(true);
                msg.setMessage("Extract Gagal");
                return ResponseEntity.ok().body(msg);
            }else {
                msg.setStatus(true);
                msg.setData(user);
                msg.setMessage("Success");
                return ResponseEntity.ok().body(msg);
            }
        }catch (Exception e){
            msg.setStatus(false);
            msg.setMessage(e.getMessage());
            return ResponseEntity.ok().body(msg);
        }
    }
}
