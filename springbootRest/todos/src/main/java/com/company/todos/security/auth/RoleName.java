package com.company.todos.security.auth;

public enum RoleName {
    SYSADMIN,
    ADMIN,
    USER;

    @Override
    public String toString() {
        return "ROLE_" + this.name();
    }
}
