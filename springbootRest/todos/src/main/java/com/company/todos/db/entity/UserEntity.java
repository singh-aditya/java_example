package com.company.todos.db.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity {
    public static final int userIdLength = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roles_id")
    private RoleEntity role;

//    @OneToMany(mappedBy = "user")
//    private Collection<TodoEntity> todos;

    // Getter and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public UserEntity setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
        return this;
    }

    public RoleEntity getRole() {
        return role;
    }

    public UserEntity setRole(RoleEntity role) {
        this.role = role;
        return this;
    }

//    public Collection<TodoEntity> getTodos() {
//        return todos;
//    }
//
//    public void setTodos(Collection<TodoEntity> todos) {
//        this.todos = todos;
//    }
}
