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

public class SetTargetPriceRequest {
    
    private Integer productId;
    private Integer shoppingListId;
    private BigDecimal targetPrice;
}

