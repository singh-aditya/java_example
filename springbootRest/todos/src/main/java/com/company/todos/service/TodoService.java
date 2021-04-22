package com.company.todos.service;

import com.company.todos.api.todo.model.Todo;

import java.util.List;

public interface TodoService {
    Todo createTodo(Todo todoIn, String userId);
    Todo getTodo(Long id, String userId);
    Todo updateTodo(Long id, Todo todoIn, String userId);
    Boolean deleteTodo(Long id, String userId);
    List<Todo> getTodos(int page, int limit, String userId);
}
