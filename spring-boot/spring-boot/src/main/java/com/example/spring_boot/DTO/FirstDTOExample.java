package com.example.spring_boot.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FirstDTOExample {
    
    private String id;
    private String name;
    private String category;
    private String brand;
    private double quantity;
    private String unit;
    private double price;
    private String currency;

@Override
public String toString() {
    return "Product{" +
            "name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            ", category='" + category + '\'' +
            ", unit='" + unit + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            ", currency='" + currency + '\'' +
            '}';
}
}

