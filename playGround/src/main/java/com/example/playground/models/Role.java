package com.example.playground.models;


public enum Role {


    ADMIN("ADMIN"),
    USER("USER");

    private final String role_user;

    Role(String role_user) {
        this.role_user = role_user;
    }

    @Override
    public String toString() {
        return role_user;
    }
}
