package com.example.spring_boot.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring_boot.DTO.ProductValuePerUnitDTO;
import com.example.spring_boot.Services.PriceService;

@RestController
@RequestMapping("/price")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/value-per-unit/all")
    public ResponseEntity<?> listProductsValuePerUnit() {

        List<ProductValuePerUnitDTO> products = priceService.listProductsValuePerUnit();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/value-per-unit/by-product-name")
    public ResponseEntity<?> getValuePerUnitProductByName(@RequestParam String productName) {
        List<ProductValuePerUnitDTO> products = priceService.getValuePerUnitProductByName(productName);
        return ResponseEntity.ok(products);
    }

}
