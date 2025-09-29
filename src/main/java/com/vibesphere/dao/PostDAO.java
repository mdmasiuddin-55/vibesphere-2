package com.vibesphere.dao;

import com.vibesphere.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostDAO {
    Post save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findByUserId(Long userId);
    List<Post> findAll();
    List<Post> findFeedPosts(Long userId); // posts from users that the current user follows
    void update(Post post);
    void delete(Long id);
    int getLikeCount(Long postId);
    int getCommentCount(Long postId);
    boolean isLikedByUser(Long postId, Long userId);
}