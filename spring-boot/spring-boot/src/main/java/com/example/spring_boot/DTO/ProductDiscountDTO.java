package com.example.spring_boot.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDiscountDTO {
    
    private Integer id;
    private String name;
    private String brand;
    private String category;
    private String supermarketName;
    private BigDecimal discountPercentage;
    private LocalDate fromDate;
    private LocalDate toDate;
    

@Override
public String toString() {
    return "Product{" +
            "name='" + name + '\'' +
            ", brand='" + brand + '\'' +
            ", category='" + category + '\'' +
            ", supermarket='" + supermarketName + '\'' +
            ", discount percentage='" + discountPercentage + '\'' +
            ", from_date='" + fromDate + '\'' +
            ", to_date='" + toDate + '\'' +
            '}';
}
}

