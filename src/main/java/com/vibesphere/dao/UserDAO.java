package com.vibesphere.dao;

import com.vibesphere.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void update(User user);
    void delete(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}