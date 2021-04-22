package com.company.todos.db.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "role")
    private Collection<UserEntity> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    public RoleEntity() {}
    public RoleEntity(String name) {
        this.name = name;
    }

    //Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public RoleEntity setUsers(Collection<UserEntity> users) {
        this.users = users;
        return this;
    }

    public Collection<AuthorityEntity> getAuthorities() {
        return authorities;
    }

    public RoleEntity setAuthorities(Collection<AuthorityEntity> authorities) {
        this.authorities = authorities;
        return this;
    }
}
