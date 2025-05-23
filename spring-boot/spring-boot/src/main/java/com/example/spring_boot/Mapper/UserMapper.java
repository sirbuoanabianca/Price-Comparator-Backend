package com.example.spring_boot.Mapper;

import org.mapstruct.Mapper;

import com.example.spring_boot.DTO.UserDTO;
import com.example.spring_boot.Model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
}