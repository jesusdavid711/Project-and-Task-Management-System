package com.projectmanager.presentation.dto;

/**
 * Response DTO for authentication.
 */
public class AuthResponse {

    private String token;
    private String username;
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(String token, String username, String message) {
        this.token = token;
        this.username = username;
        this.message = message;
    }

    public static AuthResponse success(String token, String username) {
        return new AuthResponse(token, username, "Authentication successful");
    }

    public static AuthResponse registered(String username) {
        return new AuthResponse(null, username, "User registered successfully");
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
