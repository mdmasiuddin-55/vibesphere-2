package com.vibesphere.dao;

import com.vibesphere.model.Post;
import com.vibesphere.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class PostDAOImpl implements PostDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final RowMapper<Post> rowMapper = (ResultSet rs, int rowNum) -> {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setUserId(rs.getLong("user_id"));
        post.setCaption(rs.getString("caption"));
        post.setMediaUrl(rs.getString("media_url"));
        post.setMediaType(Post.MediaType.valueOf(rs.getString("media_type")));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        post.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        // Set user info if available
        try {
            User user = new User();
            user.setId(rs.getLong("user_id"));
            user.setUsername(rs.getString("username"));
            user.setProfilePicture(rs.getString("profile_picture"));
            post.setUser(user);
        } catch (SQLException e) {
            // User columns might not be present in all queries
        }
        
        return post;
    };

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        } else {
            update(post);
            return post;
        }
    }

    private Post insert(Post post) {
        String sql = "INSERT INTO posts (user_id, caption, media_url, media_type) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, post.getUserId());
            ps.setString(2, post.getCaption());
            ps.setString(3, post.getMediaUrl());
            ps.setString(4, post.getMediaType().name());
            return ps;
        }, keyHolder);
        
        post.setId(keyHolder.getKey().longValue());
        return post;
    }

    private void update(Post post) {
        String sql = "UPDATE posts SET caption=?, media_url=?, media_type=?, updated_at=CURRENT_TIMESTAMP WHERE id=?";
        jdbcTemplate.update(sql, post.getCaption(), post.getMediaUrl(), post.getMediaType().name(), post.getId());
    }

    @Override
    public Optional<Post> findById(Long id) {
        String sql = "SELECT p.*, u.username, u.profile_picture FROM posts p JOIN users u ON p.user_id = u.id WHERE p.id = ?";
        List<Post> posts = jdbcTemplate.query(sql, rowMapper, id);
        return posts.stream().findFirst();
    }

    @Override
    public List<Post> findAll() {
        String sql = "SELECT p.*, u.username, u.profile_picture FROM posts p JOIN users u ON p.user_id = u.id ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Post> findByUserId(Long userId) {
        String sql = "SELECT p.*, u.username, u.profile_picture FROM posts p JOIN users u ON p.user_id = u.id WHERE p.user_id = ? ORDER BY p.created_at DESC";
        return jdbcTemplate.query(sql, rowMapper, userId);
    }

    @Override
    public List<Post> findFeedPosts(Long userId) {
        // For now, return all posts. You can implement follow-based feed later
        String sql = "SELECT p.*, u.username, u.profile_picture FROM posts p JOIN users u ON p.user_id = u.id ORDER BY p.created_at DESC LIMIT 50";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public boolean addLike(Long postId, Long userId) {
        String sql = "INSERT IGNORE INTO likes (post_id, user_id) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, postId, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean removeLike(Long postId, Long userId) {
        String sql = "DELETE FROM likes WHERE post_id = ? AND user_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, postId, userId);
        return rowsAffected > 0;
    }

    @Override
    public boolean addComment(Long postId, Long userId, String commentText) {
        String sql = "INSERT INTO comments (post_id, user_id, comment_text) VALUES (?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, postId, userId, commentText);
        return rowsAffected > 0;
    }

    @Override
    public int getLikeCount(Long postId) {
        String sql = "SELECT COUNT(*) FROM likes WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }

    @Override
    public int getCommentCount(Long postId) {
        String sql = "SELECT COUNT(*) FROM comments WHERE post_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, postId);
    }

    @Override
    public boolean isLikedByUser(Long postId, Long userId) {
        String sql = "SELECT COUNT(*) FROM likes WHERE post_id = ? AND user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, postId, userId);
        return count != null && count > 0;
    }
}