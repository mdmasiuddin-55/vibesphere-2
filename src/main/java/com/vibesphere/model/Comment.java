package com.vibesphere.model;

import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private Long postId;
    private Long userId;
    private String commentText;
    private LocalDateTime createdAt;
    private User user;
    
    // Constructors, getters, and setters...
}