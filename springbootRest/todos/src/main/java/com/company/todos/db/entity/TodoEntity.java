package com.company.todos.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(nullable = false)
    private String description;

    private Date targetDate;
    private boolean isDone;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public TodoEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TodoEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public TodoEntity setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
        return this;
    }

    public boolean isDone() {
        return isDone;
    }

    public TodoEntity setDone(boolean done) {
        isDone = done;
        return this;
    }
}
