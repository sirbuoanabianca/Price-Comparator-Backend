package com.example.spring_boot.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductValuePerUnitDTO {

    private Integer id;
    private String name;
    private String brand;
    private String category;
    private BigDecimal quantity;
    private String supermarketName;
    private BigDecimal valuePerUnit;
    private String unit;
    private BigDecimal price;

    //Only for products with active discount
    private BigDecimal discountPercentage;
    private BigDecimal discountedPrice;
    private BigDecimal discountedValuePerUnit;

    public ProductValuePerUnitDTO(Integer id, String name, String brand, String category,
                                    BigDecimal quantity, String supermarketName,
                                     BigDecimal discountPercentage, 
                                  String unit, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.quantity = quantity;
        this.supermarketName = supermarketName;
        this.discountPercentage = discountPercentage;
        this.unit = unit;
        this.price = price;
    }

}
