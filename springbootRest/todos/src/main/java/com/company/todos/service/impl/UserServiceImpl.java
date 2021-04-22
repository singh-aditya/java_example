package com.company.todos.service.impl;

import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserInputDetails;
import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.RoleRepository;
import com.company.todos.db.repository.UserRepository;
import com.company.todos.security.auth.RoleName;
import com.company.todos.security.auth.UserPrincipal;
import com.company.todos.service.UserService;
import com.company.todos.util.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public User createUser(UserInputDetails user) {
        UserEntity foundUser= userRepository.findByEmail(user.getEmail());
        if (foundUser != null) return modelMapper.map(foundUser, User.class);

        UserEntity entity = modelMapper.map(user, UserEntity.class)
                .setUserId(utils.generateUserId(UserEntity.userIdLength))
                .setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()))
                .setRole(roleRepository.findByName(RoleName.USER.toString()));

        UserEntity savedUser = userRepository.save(entity);
        return modelMapper.map(savedUser, User.class);
    }

    @Override
    public User getUser(String email) {
        UserEntity foundUser = userRepository.findByEmail(email);
        if (foundUser == null) throw new UsernameNotFoundException(email);

        return modelMapper.map(foundUser, User.class);
    }

    @Override
    public User getUserByUserId(String userId) {
        UserEntity foundUser = userRepository.findByUserId(userId);
        if (foundUser == null) throw new UsernameNotFoundException(userId);

        return modelMapper.map(foundUser, User.class);
    }

    @Override
    public User updateUser(String userId, UserInputDetails user) {
        UserEntity foundUser = userRepository.findByUserId(userId);
        if (foundUser == null) throw new UsernameNotFoundException(userId);
        foundUser.setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
        UserEntity updatedUser = userRepository.save(foundUser);
        return modelMapper.map(updatedUser, User.class);
    }

    @Override
    public Boolean deleteUser(String userId) {
        UserEntity foundUser = userRepository.findByUserId(userId);
        if (foundUser == null) throw new UsernameNotFoundException(userId);
        userRepository.delete(foundUser);
        return true;
    }

    @Override
    public List<User> getUsers(int page, int limit) {
        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        return users.stream()
                .map(entity -> modelMapper.map(entity, User.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity foundUser = userRepository.findByEmail(email);
        if (foundUser == null) throw new UsernameNotFoundException(email);

        return new UserPrincipal(foundUser);
    }
}
