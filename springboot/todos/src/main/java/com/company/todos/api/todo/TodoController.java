package com.company.todos.api.todo;

import com.company.todos.api.todo.model.Todo;
import com.company.todos.api.user.UserController;
import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserInputDetails;
import com.company.todos.security.Constants;
import com.company.todos.service.TodoService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users/{userId}/todos")
public class TodoController {

    @Autowired
    TodoService todoService;


    @Parameters({
            @Parameter(name = Constants.AUTH_HEADER_PREFIX, description = "${userController.authHeader.description}", in = ParameterIn.HEADER)
    })
    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CollectionModel<Todo> getAllTodos(@PathVariable String userId,
                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                             @RequestParam(value = "limit", defaultValue = "20") int limit) {
        List<Todo> todos= todoService.getTodos(page, limit, userId);
        List<Todo> todosWithSelfLink = todos.stream()
                .peek(todo -> {
                    Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).getTodo(userId, todo.getId()))
                            .withSelfRel();
                    todo.add(selfLink);
                }).collect(Collectors.toList());
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).getAllTodos(userId, page, limit))
                .withSelfRel();
        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(userId))
                .withRel("user");
        return CollectionModel.of(todosWithSelfLink, selfLink, userLink);
    }

    @Parameters({
            @Parameter(name = Constants.AUTH_HEADER_PREFIX, description = "${userController.authHeader.description}", in = ParameterIn.HEADER)
    })
    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EntityModel<Todo> getTodo(@PathVariable String userId, @PathVariable Long id) {
        Todo todo = todoService.getTodo(id, userId);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).getTodo(userId, id))
                .withSelfRel();
        Link todosLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).getAllTodos(userId, 1, 20))
                .withRel("todos");
        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(userId))
                .withRel("user");
        return EntityModel.of(todo, Arrays.asList(selfLink, todosLink, userLink));
    }

    @Parameters({
            @Parameter(name = Constants.AUTH_HEADER_PREFIX, description = "${userController.authHeader.description}", in = ParameterIn.HEADER)
    })
    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EntityModel<Todo> createTodo(@PathVariable String userId, @RequestBody Todo todoIn) {
        Todo todo = todoService.createTodo(todoIn, userId);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).createTodo(userId, todoIn))
                .withSelfRel();
        Link todosLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).getAllTodos(userId, 1, 20))
                .withRel("todos");
        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(userId))
                .withRel("user");
        return EntityModel.of(todo, Arrays.asList(selfLink, todosLink, userLink));
    }

    @Parameters({
            @Parameter(name = Constants.AUTH_HEADER_PREFIX, description = "${userController.authHeader.description}", in = ParameterIn.HEADER)
    })
    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public EntityModel<Todo> updateTodo(@PathVariable String userId, @PathVariable Long id, @RequestBody Todo todoIn) {
        Todo todo = todoService.updateTodo(id, todoIn, userId);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).updateTodo(userId, id, todoIn))
                .withSelfRel();
        Link todosLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TodoController.class).getAllTodos(userId, 1, 20))
                .withRel("todos");
        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(userId))
                .withRel("user");
        return EntityModel.of(todo, Arrays.asList(selfLink, todosLink, userLink));
    }

    @Parameters({
            @Parameter(name = Constants.AUTH_HEADER_PREFIX, description = "${userController.authHeader.description}", in = ParameterIn.HEADER)
    })
    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> deleteTodo(@PathVariable String userId, @PathVariable Long id){
        Boolean todoDeleted = todoService.deleteTodo(id, userId);
        return todoDeleted ?
                ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
