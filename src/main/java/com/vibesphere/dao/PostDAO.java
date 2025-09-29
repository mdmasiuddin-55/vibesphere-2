package com.vibesphere.dao;

import com.vibesphere.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostDAO {
    Post save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    List<Post> findByUserId(Long userId);
    List<Post> findFeedPosts(Long userId);
    boolean addLike(Long postId, Long userId);
    boolean removeLike(Long postId, Long userId);
    boolean addComment(Long postId, Long userId, String commentText);
    int getLikeCount(Long postId);
    int getCommentCount(Long postId);
    boolean isLikedByUser(Long postId, Long userId);
}