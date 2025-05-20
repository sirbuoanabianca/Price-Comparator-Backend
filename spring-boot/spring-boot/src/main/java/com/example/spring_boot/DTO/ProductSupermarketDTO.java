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
public class ProductSupermarketDTO {

    private Integer productId;
    private String productName;
    private String productBrand;
    private String productCategory;
    private BigDecimal quantity;
    private String supermarketName;
    private String unit;
    private BigDecimal price;
    private String currency;
    private LocalDate date;
}
