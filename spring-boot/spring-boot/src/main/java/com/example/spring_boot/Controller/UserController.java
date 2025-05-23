package com.example.spring_boot.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot.DTO.UserRequest;
import com.example.spring_boot.Mapper.UserMapper;
import com.example.spring_boot.Services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/add-user")
    public ResponseEntity<?> addUser(@RequestBody UserRequest request) {

        var userNewlyAdded = userService.addUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(userMapper.toDTO(userNewlyAdded));
    }

}
