package com.vibesphere.model;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String profilePicture;
    private String bio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors, getters, and setters
    public User() {}
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    
    // Getters and setters for all fields...
}