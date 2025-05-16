package com.example.spring_boot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot.DTO.ProductDiscountDTO;
import com.example.spring_boot.Services.DiscountService;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/best-discounts")
    public ResponseEntity<?> listProductsHighestPercentage() {

        List<ProductDiscountDTO> products = discountService.listProductsHighestPercentage();
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);

    }
}
