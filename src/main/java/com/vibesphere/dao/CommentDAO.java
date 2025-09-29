package com.vibesphere.dao;

import com.vibesphere.model.Comment;
import java.util.List;

public interface CommentDAO {
    Comment save(Comment comment);
    List<Comment> findByPostId(Long postId);
    void delete(Long commentId);
}