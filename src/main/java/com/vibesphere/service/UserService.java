package com.vibesphere.service;

import com.vibesphere.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(Long id);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    User authenticate(String username, String password);
}