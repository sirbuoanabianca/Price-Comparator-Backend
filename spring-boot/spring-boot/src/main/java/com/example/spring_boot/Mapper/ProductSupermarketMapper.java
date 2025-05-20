package com.example.spring_boot.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.example.spring_boot.DTO.ProductSupermarketDTO;
import com.example.spring_boot.Model.ProductSupermarket;

@Mapper(componentModel = "spring")
public interface ProductSupermarketMapper {

    @Mappings({
        @Mapping(source = "product.name", target = "productName"),
        @Mapping(source = "product.brand", target = "productBrand"),
        @Mapping(source = "product.category", target = "productCategory"), 
        @Mapping(source = "supermarket.name", target = "supermarketName")
    })
    ProductSupermarketDTO toDto(ProductSupermarket productSupermarket);
}