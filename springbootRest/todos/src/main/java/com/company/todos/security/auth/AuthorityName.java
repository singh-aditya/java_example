package com.company.todos.security.auth;

public enum AuthorityName {
    READ,
    WRITE,
    DELETE;

    @Override
    public String toString() {
        return  this.name() + "_AUTHORITY";
    }
}
