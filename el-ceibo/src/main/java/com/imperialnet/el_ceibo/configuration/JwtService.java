package com.imperialnet.el_ceibo.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY="1bdd312dd25cfd4683b7ab38f3345aceeb22698d97ebeaa028d21ee2c2ed4116";

    public String extractUserName(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }

    public String generateToken( UserDetails userDetails){
        return  generateToken( new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails){
        final String username= extractUserName(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));

    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public String generateToken(
            Map<String, Object> claimsAdd,
            UserDetails userDetails
    ) {
        // Agregar el rol del usuario a los claims
        if (userDetails.getAuthorities() != null) {
            claimsAdd.put("role", userDetails.getAuthorities().stream()
                    .findFirst() // Obtener el primer rol (puedes ajustar para múltiples roles)
                    .map(GrantedAuthority::getAuthority) // Obtener el nombre del rol
                    .orElse(null)); // Manejar el caso donde no haya roles
        }

        return Jwts
                .builder()
                .setClaims(claimsAdd)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Long extractUserId(String jwt) {
        return extractClaim(jwt, claims -> claims.get("userId", Long.class));
    }

    public <T> T extractClaim (String jwt, Function <Claims, T> claimsTFunction){
        final Claims claims = extactAllCalims(jwt);
        return claimsTFunction.apply(claims);

    }

    public Claims extactAllCalims(String jwt){
        return Jwts
               .parserBuilder()
               .setSigningKey(getSignInKey() )
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignInKey() {
        byte  [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
