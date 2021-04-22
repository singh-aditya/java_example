package com.company.todos.api.user;

import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserInputDetails;
import com.company.todos.security.Constants;
import com.company.todos.security.auth.AuthorityName;
import com.company.todos.security.auth.RoleName;
import com.company.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #id == principal.userId")
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public User getUser(@PathVariable String id) {
        return userService.getUserByUserId(id);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public User createUser(@RequestBody UserInputDetails userInputDetails) {
        return userService.createUser(userInputDetails);
    }

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #id == principal.userId")
    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public User updateUser(@PathVariable String id, @RequestBody UserInputDetails userInputDetails) {
        return userService.updateUser(id, userInputDetails);
    }

    @PreAuthorize("hasAuthority(T(com.company.todos.security.auth.AuthorityName).DELETE.name())")
    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Boolean userDeleted = userService.deleteUser(id);
        return userDeleted ?
                ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name())")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<User> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return userService.getUsers(page, limit);
    }
}
