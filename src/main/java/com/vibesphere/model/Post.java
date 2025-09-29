package com.vibesphere.model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private Long id;
    private Long userId;
    private String caption;
    private String mediaUrl;
    private MediaType mediaType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User user;
    private int likeCount;
    private int commentCount;
    private boolean likedByCurrentUser;
    private List<Comment> comments;
    
    public enum MediaType {
        IMAGE, VIDEO
    }
    
    // Constructors, getters, and setters...
}