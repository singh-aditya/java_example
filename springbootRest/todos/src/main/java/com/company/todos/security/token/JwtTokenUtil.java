package com.company.todos.security.token;

import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.UserRepository;
import com.company.todos.security.Constants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    UserRepository userRepository;

    private final Clock clock = DefaultClock.INSTANCE;

    // Used by UT
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getTokenFromReq(HttpServletRequest req) {
        String token = req.getHeader(Constants.AUTH_HEADER_PREFIX);
        if (token == null || !token.startsWith(Constants.TOKEN_PREFIX))
            return null;
        return token.replace(Constants.TOKEN_PREFIX, "");
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims != null ? claimsResolver.apply(claims) : null;
    }

    public String generateToken(String userName) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, Constants.getTokenSecret())
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return token != null
                && !isTokenExpired(token)
                && validateTokenUser(token);
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        if (claims == null)  return null;

        claims.setIssuedAt(createdDate)
                .setExpiration(expirationDate);

        return Jwts.builder().
                setClaims(claims).
                signWith(SignatureAlgorithm.HS512, Constants.getTokenSecret())
                .compact();
    }

    public Boolean validateToken(String token, String userName) {
        String tokenUser = getUsernameFromToken(token);
        return tokenUser != null && tokenUser.equals(userName) && !isTokenExpired(token);
    }

    public UserEntity getTokenUser(String token) {
        if (token == null) return null;

        String username = getUsernameFromToken(token);

        if (username == null) return null;

        return userRepository.findByEmail(username);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().
                    setSigningKey(Constants.getTokenSecret()).
                    parseClaimsJws(token).
                    getBody();
        } catch (ExpiredJwtException ex) {
            return null;
        }
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration == null || expiration.before(clock.now());
    }

    private boolean validateTokenUser(String token) {
        return getTokenUser(token) != null;
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + Constants.getTokenExpirationTimeInSeconds() * 1000);
    }
}
