package com.vibesphere.service;

import com.vibesphere.dao.PostDAO;
import com.vibesphere.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDAO postDAO;

    @Override
    public Post createPost(Post post) {
        return postDAO.save(post);
    }

    @Override
    public List<Post> getFeedPosts(Long userId) {
        return postDAO.findFeedPosts(userId);
    }

    @Override
    public List<Post> getAllPosts() {
        return postDAO.findAll();
    }

    @Override
    public List<Post> getPostsByUser(Long userId) {
        return postDAO.findByUserId(userId);
    }

    @Override
    public boolean likePost(Long postId, Long userId) {
        return postDAO.addLike(postId, userId);
    }

    @Override
    public boolean addComment(Long postId, Long userId, String commentText) {
        return postDAO.addComment(postId, userId, commentText);
    }

    @Override
    public Post getPostById(Long postId) {
        return postDAO.findById(postId).orElse(null);
    }
}