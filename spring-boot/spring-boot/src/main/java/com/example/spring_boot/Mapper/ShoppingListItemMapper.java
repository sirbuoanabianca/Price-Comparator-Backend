package com.example.spring_boot.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.spring_boot.DTO.ShoppingListItemDTO;
import com.example.spring_boot.Model.ShoppingListItem;

@Mapper(componentModel = "spring")
public interface ShoppingListItemMapper {
  @Mappings({
        @Mapping(source = "product.id", target = "productId"),
        @Mapping(source = "product.name", target = "productName"),
        @Mapping(source = "product.brand", target = "productBrand"),
        @Mapping(source = "product.category", target = "productCategory"), 
        @Mapping(source = "shoppingList.name", target = "shoppingListName"),
        @Mapping(source = "shoppingList.id", target = "shoppingListId")
    })
    ShoppingListItemDTO toDTO(ShoppingListItem shoppingList);
}