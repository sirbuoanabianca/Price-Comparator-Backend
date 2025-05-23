package com.example.spring_boot.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.spring_boot.Model.User;
import com.example.spring_boot.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(String email, String pass) {
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(pass);
        return userRepository.save(entity);
    }
}
