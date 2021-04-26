package com.company.todos.service.impl;

import com.company.todos.api.todo.model.Todo;
import com.company.todos.db.entity.TodoEntity;
import com.company.todos.db.entity.UserEntity;
import com.company.todos.db.repository.TodoRepository;
import com.company.todos.db.repository.UserRepository;
import com.company.todos.exception.TodoServiceException;
import com.company.todos.service.TodoService;
import org.hibernate.PropertyValueException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Todo createTodo(Todo todoIn, String userId) {
        UserEntity foundUser = findUser(userId);

        TodoEntity todoEntity = modelMapper.map(todoIn, TodoEntity.class)
                .setUser(foundUser);
        try {
            TodoEntity savedTodo = todoRepository.save(todoEntity);
            return modelMapper.map(savedTodo, Todo.class);
        } catch (DataIntegrityViolationException ex) {
            throw new TodoServiceException(ex.getMessage());
        }
    }

    @Override
    public Todo getTodo(Long id, String userId) {
        UserEntity foundUser = findUser(userId);
        TodoEntity foundTodo = todoRepository.findByIdAndUser(id, foundUser);
        if (foundTodo == null) throw new UsernameNotFoundException(userId + "/todos/" + id.toString());
        return modelMapper.map(foundTodo, Todo.class);
    }

    @Override
    public Todo updateTodo(Long id, Todo todoIn, String userId) {
        UserEntity foundUser = findUser(userId);
        TodoEntity foundTodo = todoRepository.findByIdAndUser(id, foundUser);
        if (foundTodo == null) throw new UsernameNotFoundException(userId + "/todos/" + id.toString());
        foundTodo.setDescription(todoIn.getDescription())
                .setTargetDate(todoIn.getTargetDate())
                .setDone(todoIn.isDone());
        try {
            TodoEntity updatedTodo = todoRepository.save(foundTodo);
            return modelMapper.map(updatedTodo, Todo.class);
        } catch (DataIntegrityViolationException ex) {
            throw new TodoServiceException(ex.getMessage());
        }
    }

    @Override
    public Boolean deleteTodo(Long id, String userId) {
        UserEntity foundUser = findUser(userId);
        TodoEntity foundTodo = todoRepository.findByIdAndUser(id, foundUser);
        if (foundTodo == null) return false;
        todoRepository.delete(foundTodo);
        return true;
    }

    @Override
    public List<Todo> getTodos(int page, int limit, String userId) {
        UserEntity foundUser = findUser(userId);

        if(page > 0) page = page - 1;

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<TodoEntity> todoPage = todoRepository.findAllByUser(pageableRequest, foundUser);
        return todoPage.getContent().stream()
                .map(todoEntity -> modelMapper.map(todoEntity, Todo.class))
                .collect(Collectors.toList());
    }

    private UserEntity findUser(String userId) {
        UserEntity foundUser = userRepository.findByUserId(userId);
        if (foundUser == null) throw new UsernameNotFoundException(userId);
        return foundUser;
    }
}
