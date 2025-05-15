package com.example.spring_boot.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {
    
    private String id;
    private String name;
    private String category;
    private String brand;

@Override
public String toString() {
    return "Product{" +
            "name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            ", category='" + category + '\'' +
            '}';
}
}

