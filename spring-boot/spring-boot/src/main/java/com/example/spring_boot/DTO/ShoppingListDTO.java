package com.example.spring_boot.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ShoppingListDTO {
    
    private Integer id;
    private String name;
    private Integer user_id;

    public ShoppingListDTO(String name, Integer user_id) {
        this.name = name;
        this.user_id = user_id;
    }
}

