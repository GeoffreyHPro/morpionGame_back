package com.example.demo.repository.userRepository;

import com.example.demo.model.User;

public interface CustomUserRepository  {
    boolean saveUser(User user);
    boolean changePassword(User user, String password);
}
