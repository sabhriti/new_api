package org.sabhriti.api.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.sabhriti.api.dal.model.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import static org.sabhriti.api.service.security.AuthenticationManager.AUTHORITIES_KEY;

@Component
public class TokenProvider implements Serializable {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Integer tokenValidity;

    public String getUsernameFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(this.getAllClaimsFromToken(token));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.generateSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return this.getExpirationDateFromToken(token).before(new Date());
    }

    public SecretKey generateSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(this.secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateToken(User user) {
        long nowMillis = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(AUTHORITIES_KEY, user.getRoles())
                .signWith(this.generateSigningKey())
                .setIssuedAt(new Date(nowMillis))
                .setExpiration(new Date(nowMillis + this.tokenValidity * 1000))
                .compact();
    }
}
