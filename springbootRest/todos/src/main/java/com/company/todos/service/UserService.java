package com.company.todos.service;

import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserInputDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User createUser(UserInputDetails user);
    User getUser(String email);
    User getUserByUserId(String userId);
    User updateUser(String userId, UserInputDetails user);
    Boolean deleteUser(String userId);
    List<User> getUsers(int page, int limit);
}
