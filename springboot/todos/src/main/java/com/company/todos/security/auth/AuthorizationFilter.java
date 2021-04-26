package com.company.todos.security.auth;

import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.UserRepository;
import com.company.todos.security.Constants;
import com.company.todos.security.token.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public AuthorizationFilter(AuthenticationManager authenticationManager,
                               JwtTokenUtil jwtTokenUtil) {
        super(authenticationManager);
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public AuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint,
                               JwtTokenUtil jwtTokenUtil) {
        super(authenticationManager, authenticationEntryPoint);
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        String token = jwtTokenUtil.getTokenFromReq(req);

        if (token != null) {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                doAuthentication(req);
            }
        }
        chain.doFilter(req, res);
    }

    private void doAuthentication(HttpServletRequest req) {
        String token = jwtTokenUtil.getTokenFromReq(req);
        UserEntity userEntity = jwtTokenUtil.getTokenUser(token);

        if (userEntity == null) return;
        UserPrincipal userPrincipal = new UserPrincipal(userEntity);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userPrincipal,
                null, userPrincipal.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }
}
