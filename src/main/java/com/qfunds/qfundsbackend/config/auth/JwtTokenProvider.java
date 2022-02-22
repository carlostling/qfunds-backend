package com.qfunds.qfundsbackend.config.auth;

import com.qfunds.qfundsbackend.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds;

    @Value("${security.jwt.token.header-string:Authorization}")
    private String headerString;

    @Value("${security.jwt.token.token-prefix:Bearer }")
    private String tokenPrefix;

    @Autowired
    private UserDetailsService userService;

    private byte[] decodedSecretKey;

    @PostConstruct
    protected void init() {
        decodedSecretKey = TextCodec.BASE64.decode(secretKey);
    }


    public String createToken(String username, User.Role role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role.name());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, decodedSecretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).
                getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(headerString);
        if (bearerToken != null && bearerToken.startsWith(tokenPrefix)) {
            return bearerToken.substring(tokenPrefix.length(), bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtTokenInvalidException("Expired or invalid JWT token");
        }
    }



}