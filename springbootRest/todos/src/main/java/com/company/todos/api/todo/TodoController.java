package com.company.todos.api.todo;

import com.company.todos.api.todo.model.Todo;
import com.company.todos.api.user.model.User;
import com.company.todos.api.user.model.UserInputDetails;
import com.company.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<Todo> getAllTodos(@PathVariable String userId,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "limit", defaultValue = "20") int limit) {
        return todoService.getTodos(page, limit, userId);
    }

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Todo getTodo(@PathVariable String userId, @PathVariable Long id) {
        return todoService.getTodo(id, userId);
    }

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Todo createTodo(@PathVariable String userId, @RequestBody Todo todoIn) {
        return todoService.createTodo(todoIn, userId);
    }

    @PreAuthorize("hasRole(T(com.company.todos.security.auth.RoleName).ADMIN.name()) or #userId == principal.userId")
    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public Todo updateTodo(@PathVariable String userId, @PathVariable Long id, @RequestBody Todo todoIn) {
        return todoService.updateTodo(id, todoIn, userId);
    }

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
