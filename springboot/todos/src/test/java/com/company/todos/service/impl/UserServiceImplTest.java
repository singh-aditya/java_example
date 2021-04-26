package com.company.todos.service.impl;

import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserInputDetails;
import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.RoleRepository;
import com.company.todos.db.repository.UserRepository;
import com.company.todos.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;

    private final String EMAIL = "abc@company.com";


    @BeforeEach
    void setUp() {
        userService.utils = new Utils();
        userService.modelMapper = new ModelMapper();
        userService.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    void createUser() {
        Mockito.when(userRepository.findByEmail(EMAIL)).thenReturn(null);
        UserInputDetails user = new UserInputDetails();
        user.setEmail(EMAIL);
        user.setFirstName("abc");
        user.setLastName("abc");
        user.setPassword("abc");
        UserEntity entity = userService.modelMapper.map(user, UserEntity.class)
                .setUserId("abc");
        Mockito.when(roleRepository.findByName(anyString())).thenReturn(null);
        Mockito.when(userRepository.save(any())).thenReturn(entity);

        User dbUser = userService.createUser(user);

        assertNotNull(dbUser);
        assertEquals(user.getFirstName(), dbUser.getFirstName());
        assertEquals(user.getEmail(), dbUser.getEmail());
        assertEquals(entity.getUserId(), dbUser.getUserId());
    }

    @Test
    void getUser() {
        UserEntity entity = new UserEntity()
                .setUserId("abc")
                .setFirstName("abc")
                .setEmail(EMAIL);
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(entity);

        User dbUser = userService.getUser(EMAIL);

        assertNotNull(dbUser);
        assertEquals(entity.getFirstName(), dbUser.getFirstName());
        assertEquals(entity.getEmail(), dbUser.getEmail());
        assertEquals(entity.getUserId(), dbUser.getUserId());
    }

    @Test
    void getUserNotFound() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.getUser(EMAIL));
    }
}