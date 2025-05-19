package com.example.spring_boot.DTO;

import java.math.BigDecimal;

import com.example.spring_boot.Model.Product;
import com.example.spring_boot.Model.ShoppingList;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShoppingListItemDTO {
    
    private Integer id;
    private String productName;
    private String productBrand;
    private String productCategory; 
    private Integer productId; 
    private Integer shoppingListId; 
    private String shoppingListName;
    private BigDecimal quantityDesired;
    private BigDecimal targetPrice;
    private Boolean ignoreBrand;


    public ShoppingListItemDTO(String productName, String productBrand, String productCategory, Integer ShoppingListId, String ShoppingListName,BigDecimal quantityDesired) {
        this.productName= productName;
        this.productBrand= productBrand;
        this.productCategory= productCategory;
        this.shoppingListId= ShoppingListId;
        this.shoppingListName= ShoppingListName;
        this.quantityDesired = quantityDesired;
    }
}

