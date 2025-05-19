package com.example.spring_boot.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.spring_boot.DTO.ShoppingListDTO;
import com.example.spring_boot.Model.ShoppingList;

@Mapper(componentModel = "spring")
public interface ShoppingListMapper {

    @Mapping(source = "user.id", target = "user_id")
    ShoppingListDTO toDTO(ShoppingList shoppingList);
}