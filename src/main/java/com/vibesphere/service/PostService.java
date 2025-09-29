package com.vibesphere.service;

import com.vibesphere.model.Post;
import java.util.List;

public interface PostService {
    Post createPost(Post post);
    List<Post> getFeedPosts(Long userId);
    List<Post> getAllPosts();
    List<Post> getPostsByUser(Long userId);
    boolean likePost(Long postId, Long userId);
    boolean addComment(Long postId, Long userId, String commentText);
    Post getPostById(Long postId);
}