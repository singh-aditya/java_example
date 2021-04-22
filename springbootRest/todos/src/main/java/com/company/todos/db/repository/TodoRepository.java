package com.company.todos.db.repository;

import com.company.todos.db.entity.TodoEntity;
import com.company.todos.db.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends PagingAndSortingRepository<TodoEntity, Long> {
    Page<TodoEntity> findAllByUser(Pageable pageableRequest, UserEntity user);
    TodoEntity findByIdAndUser(Long id, UserEntity user);

}
