package com.company.todos.security.token;

import com.company.todos.api.user.model.User;
import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class JwtTokenUtilTest {

    @Mock
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private final String USER_NAME = "abc@company.com";
    private String token;


    @BeforeEach
    public void setUp() {
        token = jwtTokenUtil.generateToken(USER_NAME);
        jwtTokenUtil.setUserRepository(userRepository);
    }
    @Test
    void canTokenBeRefreshed() {
        UserEntity user = new UserEntity();
        Mockito.when(userRepository.findByEmail(USER_NAME)).thenReturn(user);
        assertTrue(jwtTokenUtil.canTokenBeRefreshed(token));
    }

    @Test
    void validateToken() {
        assertTrue(jwtTokenUtil.validateToken(token, USER_NAME));
    }

    @Test
    void validateExpiredToken() {
        //Expired token
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYmNAY29tcGFueS5jb20iLCJpYXQiOjE2MTkzNDI5MTMsImV4cCI6MTYxOTM0MjkyM30.FihdKK2TFwxg248aO0CJDkrL1Akhvumg-vhAiGeP6qcOFY6w2pPws1YSR_JOLWX8BWs-ZXZKiepp7d7gc_zt8A";
        assertFalse(jwtTokenUtil.validateToken(token, USER_NAME));
    }

}