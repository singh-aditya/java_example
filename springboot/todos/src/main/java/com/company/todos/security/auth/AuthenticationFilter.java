package com.company.todos.security.auth;

import com.company.todos.AppContext;
import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserLoginDetails;
import com.company.todos.security.Constants;
import com.company.todos.security.token.JwtTokenUtil;
import com.company.todos.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        jwtTokenUtil = (JwtTokenUtil) AppContext.getBean("jwtTokenUtil");
        userService = (UserService) AppContext.getBean("userServiceImpl");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserLoginDetails creds = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginDetails.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>())
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String userName = ((UserPrincipal) auth.getPrincipal()).getUsername();

        String token = jwtTokenUtil.generateToken(userName);
        User user = userService.getUser(userName);

        res.addHeader(Constants.AUTH_HEADER_PREFIX, Constants.TOKEN_PREFIX + token);
        res.addHeader(Constants.USERID_HEADER_PREFIX, user.getUserId());
    }
}
