package com.projectmanager.domain.model;

import java.util.UUID;

/**
 * Domain entity representing a User in the system.
 * Pure domain model - no framework dependencies.
 */
public class User {

    private final UUID id;
    private final String username;
    private final String email;
    private final String password;

    public User(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User create(String username, String email, String password) {
        return new User(UUID.randomUUID(), username, email, password);
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
