package com.example.msorder.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Named
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "roles";
    private static final String SECRET = "R9kV7E4oLj4xEMFJiqixz2Umjbviyp";

    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        String secret = Base64.getEncoder().encodeToString(SECRET.getBytes(StandardCharsets.UTF_8));
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication) {
        var username = authentication.getName();
        var authorities = authentication.getAuthorities();
        var claims = Jwts.claims().setSubject(username);

        if (!authorities.isEmpty()) {
            claims.put(AUTHORITIES_KEY, authorities
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(",")));
        }

        var now = new Date();
        var validity = new Date(now.getTime() + this.getValidityInMs());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        var claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        var authoritiesClaim = claims.get(AUTHORITIES_KEY);
        var authorities =
                authoritiesClaim == null
                        ? AuthorityUtils.NO_AUTHORITIES
                        : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());
        var principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        return true;
    }

    private long getValidityInMs() {
        return 60L * 60 * 1000; // 1 hour
    }
}
